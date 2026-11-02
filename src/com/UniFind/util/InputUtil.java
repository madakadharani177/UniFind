package com.UniFind.util;


import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

import java.time.format.DateTimeParseException;

import java.util.Scanner;


public final class InputUtil {


    private static final DateTimeFormatter DATE_FORMAT =

            DateTimeFormatter.ofPattern("dd-MM-yyyy");


    private InputUtil() {

    }


    public static int readInt(Scanner sc, String prompt, int min, int max) {

        while (true) {

            System.out.print(prompt);

            String input = sc.nextLine().trim();


            try {

                int value = Integer.parseInt(input);


                if (value >= min && value <= max) {

                    return value;

                }

            } catch (NumberFormatException ignored) {

            }


            System.out.println("⚠ Invalid choice. Enter a number from "

                    + min + " to " + max + ".");

        }

    }


    public static String required(Scanner sc, String prompt) {

        while (true) {

            System.out.print(prompt);

            String value = sc.nextLine().trim();


            if (!value.isEmpty()) {

                return value;

            }


            System.out.println("⚠ This field is required.");

        }

    }


    public static String email(Scanner sc) {

        while (true) {

            String value = required(sc, "Enter your email: ");


            if (value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

                return value.toLowerCase();

            }


            System.out.println("⚠ Invalid email format.");

        }

    }


    public static String phone(Scanner sc) {

        while (true) {

            String value = required(sc, "Enter your phone: ");


            if (value.matches("\\d{10}")) {

                return value;

            }


            System.out.println("⚠ Phone number must contain exactly 10 digits.");

        }

    }


    public static String password(Scanner sc, String prompt) {

        while (true) {

            String value = required(sc, prompt);


            if (value.length() >= 4 && value.matches(".*\\d.*")) {

                return value;

            }


            System.out.println(

                    "⚠ Password must be at least 4 characters and contain a digit.");

        }

    }


    public static String date(Scanner sc, String prompt) {

        while (true) {

            String value = required(sc, prompt);


            try {

                LocalDate.parse(value, DATE_FORMAT);

                return value;

            } catch (DateTimeParseException e) {

                System.out.println("⚠ Invalid date. Use dd-mm-yyyy.");

            }

        }

    }


    public static void pressEnter(Scanner sc) {

        System.out.println();

        System.out.print("Press Enter to continue...");

        sc.nextLine();

    }

}

