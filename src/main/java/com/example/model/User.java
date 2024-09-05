package com.example.model;

public class User {
    private String username;
    private String role;
    private String password;

    public User(String username, String role, String password) {
        this.username = username;
        this.role = role;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
}
