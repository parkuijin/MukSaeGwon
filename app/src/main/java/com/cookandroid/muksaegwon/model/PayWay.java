package com.cookandroid.muksaegwon.model;

public class PayWay {
    private String cash;
    private String card;
    private String account;

    public PayWay(String cash, String card, String account) {
        this.cash = cash;
        this.card = card;
        this.account = account;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
