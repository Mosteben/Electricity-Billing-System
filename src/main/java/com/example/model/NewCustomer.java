package com.example.model;

public class NewCustomer extends Customer {
    private String propertyDetails;
    private String contractAttachment;
    private String password;

    // Adjust the constructor to include the appropriate parameters
    public NewCustomer(String meterCode, String name, String email, String propertyDetails, String contractAttachment, String password) {
        super(meterCode, name, email);
        this.propertyDetails = propertyDetails;
        this.contractAttachment = contractAttachment;
        this.password = password;
    }

    public String getPropertyDetails() {
        return propertyDetails;
    }

    public void setPropertyDetails(String propertyDetails) {
        this.propertyDetails = propertyDetails;
    }

    public String getContractAttachment() {
        return contractAttachment;
    }

    public void setContractAttachment(String contractAttachment) {
        this.contractAttachment = contractAttachment;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
