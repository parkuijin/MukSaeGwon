package com.cookandroid.muksaegwon.model;

import java.io.Serializable;

public class StoreSerializable implements Serializable {

    /*private static final long serialVersionUID = 1L;*/

    private String storeName;
    private String payWay;
    private String runDay;
    private String openTime, offTime;
    private String category;
    private String menus;

    public StoreSerializable() {}

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }

}
