package com.cookandroid.muksaegwon.model;

public class Store {
    private String storeName;
    private double lat;
    private double lng;
    private String[] menu;
    private String payWay;
    private short isRunning;
    private String runDay;
    private String openTime, offTime;

    public Store(String storeName, double lat, double lng, String[] menu, String payWay, short isRunning, String runDay, String openTime, String offTime) {
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

    public String[] getMenu() {
        return menu;
    }

    public void setMenu(String[] menu) {
        this.menu = menu;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
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
