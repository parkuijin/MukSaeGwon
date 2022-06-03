package com.cookandroid.muksaegwon.model;

public class Favorite {
    int storeId;
    String storeName;
    String storeLocation;

    public Favorite(int storeId, String storeName, String storeLocation) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeLocation = storeLocation;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }
}