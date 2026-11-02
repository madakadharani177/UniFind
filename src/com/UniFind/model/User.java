package com.UniFind.model;


import java.time.LocalDate;


public class User {


    private final int id;

    private String name;

    private final String email;

    private String phone;

    private String password;

    private final String role;

    private final LocalDate memberSince;


    public User(int id, String name, String email, String phone,

                String password, String role) {

        this.id = id;

        this.name = name;

        this.email = email;

        this.phone = phone;

        this.password = password;

        this.role = role;

        this.memberSince = LocalDate.now();

    }


    public int getId() {

        return id;

    }


    public String getName() {

        return name;

    }


    public String getEmail() {

        return email;

    }


    public String getPhone() {

        return phone;

    }


    public String getPassword() {

        return password;

    }


    public String getRole() {

        return role;

    }


    public LocalDate getMemberSince() {

        return memberSince;

    }


    public void setName(String name) {

        this.name = name;

    }


    public void setPhone(String phone) {

        this.phone = phone;

    }


    public void setPassword(String password) {

        this.password = password;

    }

}

