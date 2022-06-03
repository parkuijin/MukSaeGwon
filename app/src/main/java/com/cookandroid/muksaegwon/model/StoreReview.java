package com.cookandroid.muksaegwon.model;

public class StoreReview {
    private String userBy;
    private String review;
    private String date;
    private float rating;

    public StoreReview(String userBy, String review, String date, float rating) {
        this.userBy = userBy;
        this.review = review;
        this.date = date;
        this.rating = rating;
    }

    public String getUserBy() {
        return userBy;
    }

    public String getReview() {
        return review;
    }

    public String getDate() {
        return date;
    }

    public float getRating() {
        return rating;
    }
}
