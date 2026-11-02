package com.UniFind;


import java.util.Scanner;


import com.UniFind.service.ClaimService;

import com.UniFind.service.ItemService;

import com.UniFind.service.MatchingService;

import com.UniFind.service.NotificationService;

import com.UniFind.service.UserService;

import com.UniFind.util.InputUtil;


public class Main {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


        while (true) {

            printMainMenu();


            int choice = InputUtil.readInt(sc, "Enter your choice: ", 1, 3);


            switch (choice) {

                case 1:

                    UserService.register(sc);

                    break;


                case 2:

                    if (AppData.users.isEmpty()) {

                        System.out.println();

                        System.out.println("⚠ No registered users found!");

                        System.out.println("Please register first, then login later.");

                    } else {

                        UserService.login(sc);

                    }

                    break;


                case 3:

                    System.out.println();

                    System.out.println("========================================================");

                    System.out.println("           THANK YOU FOR USING UNIFIND");

                    System.out.println("========================================================");

                    System.out.println();

                    System.out.println("Application closed successfully.");

                    sc.close();

                    return;


                default:

                    break;

            }

        }

    }


    private static void printMainMenu() {

        System.out.println();

        System.out.println("========================================================");

        System.out.println("                       UNIFIND");

        System.out.println("              UNIVERSITY LOST & FOUND");

        System.out.println("========================================================");

        System.out.println();

        System.out.println("Welcome to UniFind!");

        System.out.println();

        System.out.println("1. Register");

        System.out.println("2. Login");

        System.out.println("3. Exit");

        System.out.println();

    }


    public static void showUserMenu(Scanner sc) {


        while (AppData.loggedInUser != null) {


            System.out.println();

            System.out.println("========================================================");

            System.out.println("                 --- UniFind User Menu ---");

            System.out.println("========================================================");

            System.out.println();

            System.out.println("1. Report Lost Item");

            System.out.println("2. Report Found Item");

            System.out.println("3. View My Reports");

            System.out.println("4. Search Items");

            System.out.println("5. View Notifications");

            System.out.println("6. View Possible Matches");

            System.out.println("7. Submit Claim");

            System.out.println("8. My Profile");

            System.out.println("9. Logout");

            System.out.println();


            int choice = InputUtil.readInt(sc, "Enter your choice: ", 1, 9);


            switch (choice) {

                case 1:

                    ItemService.reportLostItem(sc);

                    break;

                case 2:

                    ItemService.reportFoundItem(sc);

                    break;

                case 3:

                    ItemService.viewMyReports();

                    InputUtil.pressEnter(sc);

                    break;

                case 4:

                    ItemService.searchItems(sc);

                    break;

                case 5:

                    NotificationService.viewNotifications(sc);

                    break;

                case 6:

                    MatchingService.viewPossibleMatches(sc);

                    break;

                case 7:

                    ClaimService.submitClaim(sc);

                    break;

                case 8:

                    UserService.profile(sc);

                    break;

                case 9:

                    AppData.loggedInUser = null;

                    System.out.println();

                    System.out.println("You have been logged out.");

                    System.out.println("Thank you for using UniFind!");

                    break;

                default:

                    break;

            }

        }

    }

}


