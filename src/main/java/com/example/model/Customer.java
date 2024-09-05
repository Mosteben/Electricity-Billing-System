package com.example.model;

public abstract class Customer {
    private String meterCode;
    private String name;
    private String email;
    private String password;

    public Customer(String meterCode, String name, String email) {
        this.meterCode = meterCode;
        this.name = name;
        this.email = email;
    }

    public String getMeterCode() {
        return meterCode;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String email) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
}
