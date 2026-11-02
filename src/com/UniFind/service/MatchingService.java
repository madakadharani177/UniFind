package com.UniFind.service;


import java.util.Scanner;


import com.UniFind.AppData;

import com.UniFind.model.FoundItem;

import com.UniFind.model.LostItem;

import com.UniFind.model.Match;

import com.UniFind.model.Notification;

import com.UniFind.util.InputUtil;


public final class MatchingService {


    private MatchingService() {

    }


    public static int matchNewFoundItem(FoundItem found) {


        int count = 0;


        for (LostItem lost : AppData.lostItems) {


            if (!"ACTIVE".equals(lost.getStatus())) {

                continue;

            }


            int score = calculateScore(lost, found);


            if (score >= 50) {


                Match match = new Match(

                        AppData.nextMatchId(),

                        lost.getId(),

                        found.getId(),

                        score

                );


                AppData.matches.add(match);


                AppData.notifications.add(

                        new Notification(

                                AppData.nextNotificationId(),

                                lost.getUserId(),

                                lost.getId(),

                                found.getId(),

                                match.getId()

                        )

                );


                count++;

            }

        }


        return count;

    }


    public static void matchNewLostItem(LostItem lost) {


        for (FoundItem found : AppData.foundItems) {


            if (!"ACTIVE".equals(found.getStatus())) {

                continue;

            }


            int score = calculateScore(lost, found);


            if (score >= 50) {


                Match match = new Match(

                        AppData.nextMatchId(),

                        lost.getId(),

                        found.getId(),

                        score

                );


                AppData.matches.add(match);


                AppData.notifications.add(

                        new Notification(

                                AppData.nextNotificationId(),

                                lost.getUserId(),

                                lost.getId(),

                                found.getId(),

                                match.getId()

                        )

                );

            }

        }

    }


    public static int calculateScore(LostItem lost, FoundItem found) {


        int score = 0;


        // Name = 20

        if (same(lost.getName(), found.getName())) score += 20;


        // Category = 20

        if (same(lost.getCategory(), found.getCategory())) score += 20;


        // Brand = 20

        if (same(lost.getBrand(), found.getBrand())) score += 20;


        // Color = 15

        if (same(lost.getColor(), found.getColor())) score += 15;


        // Location = 15

        if (same(lost.getLocation(), found.getLocation())) score += 15;


        // Description = 10

        if (similar(lost.getDescription(), found.getDescription())) score += 10;


        return score;

    }


    private static boolean same(String a, String b) {

        return a.equalsIgnoreCase(b);

    }


    private static boolean similar(String a, String b) {


        String first = a.toLowerCase();

        String second = b.toLowerCase();


        for (String word : first.split("\\s+")) {

            if (word.length() >= 4 && second.contains(word)) {

                return true;

            }

        }


        return false;

    }


    public static void viewPossibleMatches(Scanner sc) {


        int userId = AppData.loggedInUser.getId();


        System.out.println();

        System.out.println("========================================================");

        System.out.println("                 --- Possible Matches ---");

        System.out.println("========================================================");


        int count = 0;


        for (Match match : AppData.matches) {


            LostItem lost = ItemService.findLostById(match.getLostItemId());


            if (lost != null && lost.getUserId() == userId) {


                FoundItem found =

                        ItemService.findFoundById(match.getFoundItemId());


                count++;


                System.out.println();

                System.out.println("Match ID   : " + match.getId());

                System.out.println("Lost Item  : " + lost.getName());

                System.out.println("Found Item : " + found.getName());

                System.out.println("Match Score: " + match.getScore() + "%");

                System.out.println("Status     : POSSIBLE MATCH");

            }

        }


        if (count == 0) {

            System.out.println("No possible matches found.");

        } else {

            System.out.println();

            System.out.println("A possible match does not automatically prove ownership.");

        }


        InputUtil.pressEnter(sc);

    }


    public static Match findById(int id) {

        for (Match match : AppData.matches) {

            if (match.getId() == id) {

                return match;

            }

        }

        return null;

    }


    public static int countForUser(int userId) {

        int count = 0;


        for (Match match : AppData.matches) {

            LostItem lost = ItemService.findLostById(match.getLostItemId());


            if (lost != null && lost.getUserId() == userId) {

                count++;

            }

        }


        return count;

    }

}

