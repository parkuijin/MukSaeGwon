package com.cookandroid.muksaegwon.model;

public class Review {
    private String review;
    private String storeName;
    private String date;
    private float rating;

    public Review(String review, String storeName, String date, float rating) {
        this.review = review;
        this.storeName = storeName;
        this.date = date;
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(short rating) {
        this.rating = rating;
    }
}
