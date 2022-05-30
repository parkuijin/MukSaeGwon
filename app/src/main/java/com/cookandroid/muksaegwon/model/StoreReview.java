package com.cookandroid.muksaegwon.model;

public class StoreReview {
    private String review;
    private String date;
    private short rating;

    public StoreReview(String review, String date, short rating) {
        this.review = review;
        this.date = date;
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public short getRating() {
        return rating;
    }

    public void setRating(short rating) {
        this.rating = rating;
    }
}
