package com.UniFind.service;


import java.util.Scanner;


import com.UniFind.AppData;

import com.UniFind.model.FoundItem;

import com.UniFind.model.Item;

import com.UniFind.model.LostItem;

import com.UniFind.model.User;

import com.UniFind.util.InputUtil;


public final class ItemService {


    private ItemService() {

    }


    public static void reportLostItem(Scanner sc) {


        System.out.println();

        System.out.println("========================================================");

        System.out.println("                  --- Report Lost Item ---");

        System.out.println("========================================================");


        String name = InputUtil.required(sc, "Enter item name: ");

        String category = InputUtil.required(sc, "Enter category: ");

        String brand = InputUtil.required(sc, "Enter brand: ");

        String color = InputUtil.required(sc, "Enter color: ");

        String description = InputUtil.required(sc, "Enter description: ");

        String location = InputUtil.required(sc, "Enter location: ");

        String date = InputUtil.date(sc, "Enter date (dd-mm-yyyy): ");


        int id = AppData.nextLostId();


        LostItem item = new LostItem(

                id,

                AppData.loggedInUser.getId(),

                name, category, brand, color,

                description, location, date

        );


        AppData.lostItems.add(item);


        System.out.println();

        System.out.println("Lost item reported successfully!");

        System.out.println("Report ID: " + id);

        System.out.println("Status: ACTIVE");


        MatchingService.matchNewLostItem(item);


        InputUtil.pressEnter(sc);

    }


    public static void reportFoundItem(Scanner sc) {


        System.out.println();

        System.out.println("========================================================");

        System.out.println("                 --- Report Found Item ---");

        System.out.println("========================================================");


        String name = InputUtil.required(sc, "Enter item name: ");

        String category = InputUtil.required(sc, "Enter category: ");

        String brand = InputUtil.required(sc, "Enter brand: ");

        String color = InputUtil.required(sc, "Enter color: ");

        String description = InputUtil.required(sc, "Enter description: ");

        String location = InputUtil.required(sc, "Enter location: ");

        String date = InputUtil.date(sc, "Enter date (dd-mm-yyyy): ");


        int id = AppData.nextFoundId();


        FoundItem item = new FoundItem(

                id,

                AppData.loggedInUser.getId(),

                name, category, brand, color,

                description, location, date

        );


        AppData.foundItems.add(item);


        System.out.println();

        System.out.println("Found item reported successfully!");

        System.out.println("Report ID: " + id);

        System.out.println("Status: ACTIVE");


        System.out.println();

        System.out.println("*** Matching lost items... ***");


        int matches = MatchingService.matchNewFoundItem(item);


        if (matches > 0) {

            System.out.println();

            System.out.println("A possible match was found!");

            System.out.println("Notifications sent to related users.");

        } else {

            System.out.println("No strong possible matches found.");

        }


        InputUtil.pressEnter(sc);

    }


    public static void viewMyReports() {


        int userId = AppData.loggedInUser.getId();


        System.out.println();

        System.out.println("========================================================");

        System.out.println("                    --- My Reports ---");

        System.out.println("========================================================");


        System.out.println("--- My Lost Items ---");


        boolean lostFound = false;


        for (LostItem item : AppData.lostItems) {

            if (item.getUserId() == userId) {

                lostFound = true;


                System.out.println("ID: " + item.getId()

                        + " | " + item.getName()

                        + " | " + item.getLocation()

                        + " | " + item.getStatus());

            }

        }


        if (!lostFound) {

            System.out.println("No lost items reported.");

        }


        System.out.println();

        System.out.println("--- My Found Items ---");


        boolean foundFound = false;


        for (FoundItem item : AppData.foundItems) {

            if (item.getUserId() == userId) {

                foundFound = true;


                System.out.println("ID: " + item.getId()

                        + " | " + item.getName()

                        + " | " + item.getLocation()

                        + " | " + item.getStatus());

            }

        }


        if (!foundFound) {

            System.out.println("No found items reported.");

        }

    }


    public static void searchItems(Scanner sc) {


        System.out.println();

        System.out.println("========================================================");

        System.out.println("                    --- Search Items ---");

        System.out.println("========================================================");


        String keyword = InputUtil.required(

                sc, "Enter keyword (name/category/location): ").toLowerCase();


        System.out.println();

        System.out.println("Search Results:");

        System.out.println("--------------------------------------------------------");


        int count = 0;


        for (FoundItem item : AppData.foundItems) {


            if (contains(item.getName(), keyword)

                    || contains(item.getCategory(), keyword)

                    || contains(item.getLocation(), keyword)) {


                count++;


                User finder = UserService.findById(item.getUserId());


                System.out.println(count + ". " + item.getName() + " (Found)");

                System.out.println("   Category : " + item.getCategory());

                System.out.println("   Location : " + item.getLocation());

                System.out.println("   Date     : " + item.getDate());


                if (finder != null) {

                    System.out.println("   Found by : " + finder.getName());

                }

            }

        }


        if (count == 0) {

            System.out.println("No matching items found.");

        }


        InputUtil.pressEnter(sc);

    }


    public static LostItem findLostById(int id) {

        for (LostItem item : AppData.lostItems) {

            if (item.getId() == id) {

                return item;

            }

        }

        return null;

    }


    public static FoundItem findFoundById(int id) {

        for (FoundItem item : AppData.foundItems) {

            if (item.getId() == id) {

                return item;

            }

        }

        return null;

    }


    public static int countLost(int userId) {

        int count = 0;

        for (LostItem item : AppData.lostItems) {

            if (item.getUserId() == userId) count++;

        }

        return count;

    }


    public static int countFound(int userId) {

        int count = 0;

        for (FoundItem item : AppData.foundItems) {

            if (item.getUserId() == userId) count++;

        }

        return count;

    }


    public static int countRecovered(int userId) {

        int count = 0;

        for (LostItem item : AppData.lostItems) {

            if (item.getUserId() == userId

                    && "RETURNED".equals(item.getStatus())) {

                count++;

            }

        }

        return count;

    }


    private static boolean contains(String value, String keyword) {

        return value.toLowerCase().contains(keyword);

    }


    public static void printItem(Item item) {

        System.out.println("ID          : " + item.getId());

        System.out.println("Name        : " + item.getName());

        System.out.println("Category    : " + item.getCategory());

        System.out.println("Brand       : " + item.getBrand());

        System.out.println("Color       : " + item.getColor());

        System.out.println("Description : " + item.getDescription());

        System.out.println("Location    : " + item.getLocation());

        System.out.println("Date        : " + item.getDate());

        System.out.println("Status      : " + item.getStatus());

    }

}

