package com.example.model;

public class Payment {
    private String meterCode;
    private double amount;
    private String paymentDate;

    public Payment(String meterCode, double amount, String paymentDate) {
        this.meterCode = meterCode;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public String getMeterCode() { return meterCode; }
    public double getAmount() { return amount; }
    public String getPaymentDate() { return paymentDate; }
    public void setMeterCode(String meterCode) { this.meterCode = meterCode; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }
}
