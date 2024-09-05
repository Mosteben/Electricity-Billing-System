package com.example.model;
import com.example.service.BillCalculator;

import java.util.Date;

public class Bill {
    private String meterCode;
    private Date billDate;
    private double amountDue;
    private double amountPaid;
    private Date dueDate;
    private String paymentStatus;
    private String customerName;
    private String region;
    private double previousReading;
    private double currentReading;



    public Bill(String meterCode, Date billDate, double previousReading, double currentReading, Date dueDate, String paymentStatus, String customerName, String region) {
        this.meterCode = meterCode;
        this.billDate = billDate;
        this.previousReading = previousReading;
        this.currentReading = currentReading;
        this.dueDate = dueDate;
        this.paymentStatus = paymentStatus;
        this.customerName = customerName;
        this.region = region;
        this.amountDue = calculateAmountDue();
        this.amountPaid = calculateAmountPaid();
    }

    // Getters and setters
    public String getMeterCode() {
        return meterCode;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCustomerName() {
        return customerName;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getPreviousReading() {
        return previousReading;
    }

    public void setPreviousReading(double previousReading) {
        this.previousReading = previousReading;
        this.amountDue = calculateAmountDue();
    }

    public double getCurrentReading() {
        return currentReading;
    }

    public void setCurrentReading(double currentReading) {
        this.currentReading = currentReading;
        this.amountDue = calculateAmountDue();
    }

    private double calculateAmountDue() {

        double consumption = currentReading - previousReading;
        return BillCalculator.calculateBill(consumption);

    }

    private double calculateAmountPaid() {
        return 0.0;
    }
    public double getConsumption() {
        return currentReading - previousReading;
    }
}
