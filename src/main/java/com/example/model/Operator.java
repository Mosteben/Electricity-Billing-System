package com.example.model;


public class Operator extends User {
    private String region;

    public Operator(String username, String password, String region) {
        super(username, "Operator", password);
        this.region = region;
    }

    public String getRegion() {
        return region;
    }
}

