package com.UniFind.service;


import java.util.Scanner;


import com.UniFind.AppData;

import com.UniFind.model.Claim;

import com.UniFind.model.FoundItem;

import com.UniFind.model.LostItem;

import com.UniFind.model.Match;

import com.UniFind.model.User;

import com.UniFind.util.InputUtil;


public final class ClaimService {


    private ClaimService() {

    }


    public static void submitClaim(Scanner sc) {


        int userId = AppData.loggedInUser.getId();


        System.out.println();

        System.out.println("========================================================");

        System.out.println("                    --- Submit Claim ---");

        System.out.println("========================================================");


        boolean hasMatch = false;


        for (Match match : AppData.matches) {

            LostItem lost = ItemService.findLostById(match.getLostItemId());


            if (lost != null && lost.getUserId() == userId) {

                hasMatch = true;


                System.out.println("Match ID: " + match.getId()

                        + " | " + lost.getName()

                        + " | Score: " + match.getScore() + "%");

            }

        }


        if (!hasMatch) {

            System.out.println("No possible matches available for your lost items.");

            InputUtil.pressEnter(sc);

            return;

        }


        int matchId = InputUtil.readInt(

                sc, "Enter Match ID: ", 1, Integer.MAX_VALUE);


        Match match = MatchingService.findById(matchId);


        if (match == null) {

            System.out.println("⚠ Match ID not found.");

            InputUtil.pressEnter(sc);

            return;

        }


        LostItem lost = ItemService.findLostById(match.getLostItemId());

        FoundItem found = ItemService.findFoundById(match.getFoundItemId());


        if (lost == null || found == null || lost.getUserId() != userId) {

            System.out.println("⚠ You are not authorized to claim this item.");

            InputUtil.pressEnter(sc);

            return;

        }


        if (!"ACTIVE".equals(lost.getStatus())

                || !"ACTIVE".equals(found.getStatus())) {

            System.out.println("⚠ This item is no longer available.");

            InputUtil.pressEnter(sc);

            return;

        }


        for (Claim oldClaim : AppData.claims) {

            if (oldClaim.getLostItemId() == lost.getId()

                    && oldClaim.getFoundItemId() == found.getId()

                    && !"REJECTED".equals(oldClaim.getStatus())) {


                System.out.println("⚠ A claim already exists for this match.");

                InputUtil.pressEnter(sc);

                return;

            }

        }


        System.out.println();

        System.out.println("Why do you believe this item belongs to you?");

        String reason = InputUtil.required(sc, "> ");


        Claim claim = new Claim(

                AppData.nextClaimId(),

                lost.getId(),

                found.getId(),

                userId,

                reason

        );


        AppData.claims.add(claim);


        System.out.println();

        System.out.println("✓ Claim submitted.");

        System.out.println("Claim ID: " + claim.getId());

        System.out.println("Status: PENDING");


        InputUtil.pressEnter(sc);

    }


    public static int countForUser(int userId) {

        int count = 0;


        for (Claim claim : AppData.claims) {

            if (claim.getClaimantUserId() == userId) {

                count++;

            }

        }


        return count;

    }


    public static Claim findById(int id) {

        for (Claim claim : AppData.claims) {

            if (claim.getId() == id) {

                return claim;

            }

        }

        return null;

    }

}