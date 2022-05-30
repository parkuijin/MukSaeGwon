package com.cookandroid.muksaegwon.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class Store {
    private String storeId;
    private String storeName;
    private double lat;
    private double lng;
    private JSONArray menus; // org.json 사용
    private JSONObject payWay;
    private short isRunning;
    private String runDay; // JsonObject 변경 예정
    private String openTime, offTime;
    private JSONObject category;

    public Store(String id, String storeName, double lat, double lng, JSONArray menus, JSONObject payWay, short isRunning, String runDay, String openTime, String offTime, JSONObject category) {
        this.storeId = id;
        this.storeName = storeName;
        this.lat = lat;
        this.lng = lng;
        this.menus = menus;
        this.payWay = payWay;
        this.isRunning = isRunning;
        this.runDay = runDay;
        this.openTime = openTime;
        this.offTime = offTime;
        this.category = category;
    }

    public Store(String id, String storeName, double lat, double lng) {
        this.storeId = id;
        this.storeName = storeName;
        this.lat = lat;
        this.lng = lng;
    }

    public String getId() {
        return storeId;
    }

    public void setId(String id) {
        this.storeId = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public JSONArray getMenus() {
        return menus;
    }

    public void setMenus(JSONArray menus) {
        this.menus = menus;
    }

    public JSONObject getPayWay() {
        return payWay;
    }

    public void setPayWay(JSONObject payWay) {
        this.payWay = payWay;
    }

    public short getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(short isRunning) {
        this.isRunning = isRunning;
    }

    public String getRunDay() {
        return runDay;
    }

    public void setRunDay(String runDay) {
        this.runDay = runDay;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getOffTime() {
        return offTime;
    }

    public void setOffTime(String offTime) {
        this.offTime = offTime;
    }

    public JSONObject getCategory() {
        return category;
    }

    public void setCategory(JSONObject category) {
        this.category = category;
    }
}
