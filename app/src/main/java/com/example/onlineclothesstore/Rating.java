package com.example.onlineclothesstore;

public class Rating {
    private String rating;
    private String userID;
    private String itemID;
    private String ratingID;

    public Rating(){

    }

    public String getRating() {
        return rating;
    }

    public String getUserID() {
        return userID;
    }

    public String getItemID() {
        return itemID;
    }

    public String getRatingID() {
        return ratingID;
    }

    public void setRatingID(String ratingID) {
        this.ratingID = ratingID;
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Rating(String rating, String userID, String itemID, String ratingID) {
        this.rating = rating;
        this.userID = userID;
        this.itemID = itemID;
        this.ratingID = ratingID;
    }


}
