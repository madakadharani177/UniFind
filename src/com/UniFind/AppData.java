package com.UniFind;


import java.util.ArrayList;

import java.util.List;


import com.UniFind.model.Claim;

import com.UniFind.model.FoundItem;

import com.UniFind.model.LostItem;

import com.UniFind.model.Match;

import com.UniFind.model.Notification;

import com.UniFind.model.User;


public final class AppData {


    public static final List<User> users = new ArrayList<>();

    public static final List<LostItem> lostItems = new ArrayList<>();

    public static final List<FoundItem> foundItems = new ArrayList<>();

    public static final List<Match> matches = new ArrayList<>();

    public static final List<Notification> notifications = new ArrayList<>();

    public static final List<Claim> claims = new ArrayList<>();


    public static User loggedInUser;


    private AppData() {

    }


    public static int nextUserId() {

        return 1001 + users.size();

    }


    public static int nextLostId() {

        return 101 + lostItems.size();

    }


    public static int nextFoundId() {

        return 201 + foundItems.size();

    }


    public static int nextMatchId() {

        return 301 + matches.size();

    }


    public static int nextNotificationId() {

        return 1 + notifications.size();

    }


    public static int nextClaimId() {

        return 501 + claims.size();

    }

}

