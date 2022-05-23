package com.cookandroid.muksaegwon.model;

public class Member {
    private String mId;
    private String mName;

    public Member(String mId, String mName) {
        this.mId = mId;
        this.mName = mName;
    }

    public String getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }
}
