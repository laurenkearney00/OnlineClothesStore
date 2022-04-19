package com.example.onlineclothesstore;

public class UserCustomer {

    public String fullName, dateOfBirth, phoneNumber, email, shippingAddress, paymentMethod, customerID;


    public String getFullName() {
        return fullName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setFullName(String fullName) {
        this.fullName= fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }


    public UserCustomer(){

    }

    public UserCustomer(String fullName, String dateOfBirth, String email, String phoneNumber, String shippingAddress, String paymentMethod, String customerID){
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.customerID = customerID;
    }
}

