package com.UniFind.model;


public class Notification {


    private final int id;

    private final int recipientUserId;

    private final int lostItemId;

    private final int foundItemId;

    private final int matchId;

    private boolean read;


    public Notification(int id, int recipientUserId, int lostItemId,

                        int foundItemId, int matchId) {

        this.id = id;

        this.recipientUserId = recipientUserId;

        this.lostItemId = lostItemId;

        this.foundItemId = foundItemId;

        this.matchId = matchId;

    }


    public int getId() { return id; }

    public int getRecipientUserId() { return recipientUserId; }

    public int getLostItemId() { return lostItemId; }

    public int getFoundItemId() { return foundItemId; }

    public int getMatchId() { return matchId; }

    public boolean isRead() { return read; }


    public void markRead() {

        read = true;

    }

}

