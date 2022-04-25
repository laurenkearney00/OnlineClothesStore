package com.example.onlineclothesstore;

public class Reviews {
    private String review;
    private String userID;
    private String itemID;
    private String reviewID;

    public Reviews(){

    }

    public String getReview() {
        return review;
    }

    public String getUserID() {
        return userID;
    }

    public String getItemID() {
        return itemID;
    }

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Reviews(String review, String userID, String itemID, String reviewID) {
        this.review = review;
        this.userID = userID;
        this.itemID = itemID;
        this.reviewID = reviewID;
    }


}
