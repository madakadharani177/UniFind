package com.UniFind.model;


public class Match {


    private final int id;

    private final int lostItemId;

    private final int foundItemId;

    private final int score;


    public Match(int id, int lostItemId, int foundItemId, int score) {

        this.id = id;

        this.lostItemId = lostItemId;

        this.foundItemId = foundItemId;

        this.score = score;

    }


    public int getId() { return id; }

    public int getLostItemId() { return lostItemId; }

    public int getFoundItemId() { return foundItemId; }

    public int getScore() { return score; }

}

