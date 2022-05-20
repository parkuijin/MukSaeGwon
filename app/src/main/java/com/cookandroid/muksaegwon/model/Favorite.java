package com.cookandroid.muksaegwon.model;

public class Favorite {
    int storeId;
    String storeName;

    public Favorite(int storeId, String storeName) {
        this.storeId = storeId;
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
}