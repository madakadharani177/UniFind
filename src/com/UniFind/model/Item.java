package com.UniFind.model;


public abstract class Item {


    protected int id;

    protected int userId;

    protected String name;

    protected String category;

    protected String brand;

    protected String color;

    protected String description;

    protected String location;

    protected String date;

    protected String status;


    protected Item(int id, int userId, String name, String category,

                   String brand, String color, String description,

                   String location, String date) {

        this.id = id;

        this.userId = userId;

        this.name = name;

        this.category = category;

        this.brand = brand;

        this.color = color;

        this.description = description;

        this.location = location;

        this.date = date;

        this.status = "ACTIVE";

    }


    public int getId() { return id; }

    public int getUserId() { return userId; }

    public String getName() { return name; }

    public String getCategory() { return category; }

    public String getBrand() { return brand; }

    public String getColor() { return color; }

    public String getDescription() { return description; }

    public String getLocation() { return location; }

    public String getDate() { return date; }

    public String getStatus() { return status; }


    public void setStatus(String status) {

        this.status = status;

    }

}

