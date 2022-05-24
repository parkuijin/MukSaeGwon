package com.cookandroid.muksaegwon.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class Store {
    private String storeName;
    private double lat;
    private double lng;
    private JSONArray menu; // org.json 사용
    private JSONObject payWay;
    private short isRunning;
    private String runDay; // JsonObject 변경 예정
    private String openTime, offTime;

    public Store(String storeName, double lat, double lng, JSONArray menu, JSONObject payWay, short isRunning, String runDay, String openTime, String offTime) {
        this.storeName = storeName;
        this.lat = lat;
        this.lng = lng;
        this.menu = menu;
        this.payWay = payWay;
        this.isRunning = isRunning;
        this.runDay = runDay;
        this.openTime = openTime;
        this.offTime = offTime;
    }

    public Store() {
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

    public JSONArray getMenu() {
        return menu;
    }

    public void setMenu(JSONArray menu) {
        this.menu = menu;
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
}
