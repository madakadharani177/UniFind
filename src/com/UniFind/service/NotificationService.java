package com.UniFind.service;


import java.util.Scanner;


import com.UniFind.AppData;

import com.UniFind.model.FoundItem;

import com.UniFind.model.LostItem;

import com.UniFind.model.Match;

import com.UniFind.model.Notification;

import com.UniFind.model.User;

import com.UniFind.util.InputUtil;


public final class NotificationService {


    private NotificationService() {

    }


    public static void viewNotifications(Scanner sc) {


        int userId = AppData.loggedInUser.getId();


        System.out.println();

        System.out.println("========================================================");

        System.out.println("                   --- Notifications ---");

        System.out.println("========================================================");


        int count = 0;


        for (Notification notification : AppData.notifications) {


            if (notification.getRecipientUserId() != userId) {

                continue;

            }


            count++;


            LostItem lost =

                    ItemService.findLostById(notification.getLostItemId());


            FoundItem found =

                    ItemService.findFoundById(notification.getFoundItemId());


            Match match =

                    MatchingService.findById(notification.getMatchId());


            System.out.println();

            System.out.println("You have a new notification(s).");

            System.out.println("-----------------------------------------------");

            System.out.println("Notification ID: " + notification.getId());

            System.out.println();

            System.out.println("A FOUND item matches your LOST item!");

            System.out.println("Item: " + found.getName());

            System.out.println("Location: " + found.getLocation());

            System.out.println("Found on: " + found.getDate());

            System.out.println("Match Score: " + match.getScore() + "%");


            User finder = UserService.findById(found.getUserId());


            if (finder != null) {

                System.out.println("Found by: " + finder.getName()

                        + " (User ID: " + finder.getId() + ")");

            }


            System.out.println();

            System.out.println("1. View Details");

            System.out.println("2. Back");


            int choice = InputUtil.readInt(sc, "Enter your choice: ", 1, 2);


            if (choice == 1) {

                showMatchDetails(sc, lost, found, match);

            }


            notification.markRead();

        }


        if (count == 0) {

            System.out.println("No notifications found.");

        }


        InputUtil.pressEnter(sc);

    }


    private static void showMatchDetails(Scanner sc, LostItem lost,

                                          FoundItem found, Match match) {


        System.out.println();

        System.out.println("========================================================");

        System.out.println("                --- Matched Item Details ---");

        System.out.println("========================================================");


        System.out.println();

        System.out.println("Lost Item (Your Report)");

        System.out.println("-----------------------");

        ItemService.printItem(lost);


        System.out.println();

        System.out.println("Matched Found Item");

        System.out.println("------------------");

        ItemService.printItem(found);


        System.out.println();

        System.out.println("Match Score: " + match.getScore() + "%");


        System.out.println();

        System.out.println("1. Contact Found Person");

        System.out.println("2. Back");


        int choice = InputUtil.readInt(sc, "Enter your choice: ", 1, 2);


        if (choice == 1) {

            contactFoundPerson(sc, found);

        }

    }


    private static void contactFoundPerson(Scanner sc, FoundItem found) {


        User finder = UserService.findById(found.getUserId());


        System.out.println();

        System.out.println("========================================================");

        System.out.println("                 --- Contact Details ---");

        System.out.println("========================================================");


        if (finder != null) {

            System.out.println("Name     : " + finder.getName());

            System.out.println("Phone    : " + finder.getPhone());

            System.out.println("Email    : " + finder.getEmail());

            System.out.println("Location : " + found.getLocation());

        }


        System.out.println();

        System.out.println("Please verify the item details before collecting.");

        InputUtil.pressEnter(sc);

    }


    public static int countForUser(int userId) {

        int count = 0;


        for (Notification notification : AppData.notifications) {

            if (notification.getRecipientUserId() == userId) {

                count++;

            }

        }


        return count;

    }

}

