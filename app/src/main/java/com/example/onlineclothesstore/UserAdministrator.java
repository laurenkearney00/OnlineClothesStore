package com.example.onlineclothesstore;

public class UserAdministrator {

    public String fullName, email, administratorID;


    public String getFullName() {
        return fullName;
    }

    public String getAdministratorID() {
        return administratorID;
    }

    public void setFullName(String fullName) {
        this.fullName= fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setAdministratorID(String administratorID) {
        this.administratorID = administratorID;
    }


    public UserAdministrator(){

    }

    public UserAdministrator(String fullName, String email, String administratorID){
        this.fullName = fullName;
        this.email = email;
        this.administratorID = administratorID;
    }
}

