package com.UniFind.model;


public class Claim {


    private final int id;

    private final int lostItemId;

    private final int foundItemId;

    private final int claimantUserId;

    private final String reason;

    private String status;


    public Claim(int id, int lostItemId, int foundItemId,

                 int claimantUserId, String reason) {

        this.id = id;

        this.lostItemId = lostItemId;

        this.foundItemId = foundItemId;

        this.claimantUserId = claimantUserId;

        this.reason = reason;

        this.status = "PENDING";

    }


    public int getId() { return id; }

    public int getLostItemId() { return lostItemId; }

    public int getFoundItemId() { return foundItemId; }

    public int getClaimantUserId() { return claimantUserId; }

    public String getReason() { return reason; }

    public String getStatus() { return status; }


    public void setStatus(String status) {

        this.status = status;

    }

}

