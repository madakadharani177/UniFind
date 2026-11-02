package com.UniFind.service;


import java.util.Scanner;


import com.UniFind.AppData;

import com.UniFind.Main;

import com.UniFind.model.User;

import com.UniFind.util.InputUtil;


public final class UserService {


    private UserService() {

    }


    public static void register(Scanner sc) {


        System.out.println();

        System.out.println("========================================================");

        System.out.println("                      --- Register ---");

        System.out.println("========================================================");


        String name = InputUtil.required(sc, "Enter your name: ");


        String email;

        while (true) {

            email = InputUtil.email(sc);


            if (findByEmail(email) == null) {

                break;

            }


            System.out.println("⚠ Email already registered.");

            System.out.println("Please use another email or login.");

        }


        String phone = InputUtil.phone(sc);

        String password = InputUtil.password(sc, "Enter your password: ");


        String confirm;

        while (true) {

            confirm = InputUtil.required(sc, "Confirm password: ");


            if (password.equals(confirm)) {

                break;

            }


            System.out.println("⚠ Passwords do not match. Try again.");

        }


        String role;

        while (true) {

            role = InputUtil.required(

                    sc, "Are you a student/admin? (student/admin): ");


            if (role.equalsIgnoreCase("student")

                    || role.equalsIgnoreCase("admin")) {

                role = role.toUpperCase();

                break;

            }


            System.out.println("⚠ Enter only student or admin.");

        }


        int id = AppData.nextUserId();


        User user = new User(

                id, name, email, phone, password, role

        );


        AppData.users.add(user);


        System.out.println();

        System.out.println("✓ Registration successful!");

        System.out.println("Your User ID: " + id);


        InputUtil.pressEnter(sc);

    }


    public static void login(Scanner sc) {


        System.out.println();

        System.out.println("========================================================");

        System.out.println("                       --- Login ---");

        System.out.println("========================================================");


        int attempts = 3;


        while (attempts > 0) {


            String email = InputUtil.required(sc, "Enter your email: ");

            String password = InputUtil.required(sc, "Enter password: ");


            User user = findByEmail(email);


            if (user != null && user.getPassword().equals(password)) {


                AppData.loggedInUser = user;


                System.out.println();

                System.out.println("Login successful!");

                System.out.println("Welcome, " + user.getName()

                        + " (" + prettyRole(user.getRole()) + ")");


                InputUtil.pressEnter(sc);

                Main.showUserMenu(sc);

                return;

            }


            attempts--;


            System.out.println();

            System.out.println("⚠ Incorrect email or password.");

            System.out.println("Please enter the correct credentials.");


            if (attempts > 0) {

                System.out.println("Attempts remaining: " + attempts);

            }

        }


        System.out.println("Too many failed attempts. Returning to main menu.");

    }


    public static void profile(Scanner sc) {


        User user = AppData.loggedInUser;


        while (true) {

            System.out.println();

            System.out.println("========================================================");

            System.out.println("                       MY PROFILE");

            System.out.println("========================================================");


            System.out.println("User ID       : " + user.getId());

            System.out.println("Name          : " + user.getName());

            System.out.println("Email         : " + user.getEmail());

            System.out.println("Phone         : " + user.getPhone());

            System.out.println("Role          : " + prettyRole(user.getRole()));

            System.out.println("Member Since  : " + user.getMemberSince());


            System.out.println();

            System.out.println("--------------------------------------------------------");

            System.out.println("Activity Summary");

            System.out.println("--------------------------------------------------------");

            System.out.println("Lost Reports      : " + ItemService.countLost(user.getId()));

            System.out.println("Found Reports     : " + ItemService.countFound(user.getId()));

            System.out.println("Possible Matches  : " + MatchingService.countForUser(user.getId()));

            System.out.println("Claims            : " + ClaimService.countForUser(user.getId()));

            System.out.println("Recovered Items   : " + ItemService.countRecovered(user.getId()));

            System.out.println("Notifications     : " + NotificationService.countForUser(user.getId()));


            System.out.println();

            System.out.println("1. Edit Profile");

            System.out.println("2. Change Password");

            System.out.println("3. View My Reports");

            System.out.println("4. Back");


            int choice = InputUtil.readInt(sc, "Enter your choice: ", 1, 4);


            if (choice == 1) {

                editProfile(sc);

            } else if (choice == 2) {

                changePassword(sc);

            } else if (choice == 3) {

                ItemService.viewMyReports();

                InputUtil.pressEnter(sc);

            } else {

                return;

            }

        }

    }


    private static void editProfile(Scanner sc) {

        User user = AppData.loggedInUser;


        System.out.println();

        System.out.println("--- Edit Profile ---");


        String name = InputUtil.required(sc, "Enter new name: ");

        String phone = InputUtil.phone(sc);


        user.setName(name);

        user.setPhone(phone);


        System.out.println("✓ Profile updated successfully.");

        InputUtil.pressEnter(sc);

    }


    private static void changePassword(Scanner sc) {


        User user = AppData.loggedInUser;


        System.out.println();

        System.out.println("--- Change Password ---");


        String current = InputUtil.required(sc, "Enter current password: ");


        if (!user.getPassword().equals(current)) {

            System.out.println("⚠ Current password is incorrect.");

            InputUtil.pressEnter(sc);

            return;

        }


        String newPassword = InputUtil.password(sc, "Enter new password: ");


        String confirm = InputUtil.required(sc, "Confirm new password: ");


        if (!newPassword.equals(confirm)) {

            System.out.println("⚠ Passwords do not match.");

            InputUtil.pressEnter(sc);

            return;

        }


        user.setPassword(newPassword);


        System.out.println("✓ Password changed successfully.");

        InputUtil.pressEnter(sc);

    }


    public static User findById(int id) {

        for (User user : AppData.users) {

            if (user.getId() == id) {

                return user;

            }

        }

        return null;

    }


    public static User findByEmail(String email) {

        for (User user : AppData.users) {

            if (user.getEmail().equalsIgnoreCase(email)) {

                return user;

            }

        }

        return null;

    }


    private static String prettyRole(String role) {

        return role.substring(0, 1).toUpperCase()

                + role.substring(1).toLowerCase();

    }

}

