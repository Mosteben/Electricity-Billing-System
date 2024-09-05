package com.example.model;

public class OldCustomer extends Customer {
    private double previousReading;
    private double currentReading;
    private String password;

    public OldCustomer(String meterCode, String name, String email, double previousReading, double currentReading, String password) {
        super(meterCode, name, email);
        this.previousReading = previousReading;
        this.currentReading = currentReading;
        this.password = password;
    }

    public double getPreviousReading() {
        return previousReading;
    }

    public double getCurrentReading() {
        return currentReading;
    }

    public void setPreviousReading(double previousReading) {
        this.previousReading = previousReading;
    }

    public void setCurrentReading(double currentReading) {
        this.currentReading = currentReading;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
