package com.cookandroid.muksaegwon.model;

public class Category {
    private String corn;
    private String fish;
    private String topokki;
    private String eomuk;
    private String sweetpotato;
    private String toast;
    private String takoyaki;
    private String waffle;
    private String dakggochi;

    public Category(String corn, String fish, String topokki, String eomuk, String sweetpotato, String toast, String takoyaki, String waffle, String dakggochi) {
        this.corn = corn;
        this.fish = fish;
        this.topokki = topokki;
        this.eomuk = eomuk;
        this.sweetpotato = sweetpotato;
        this.toast = toast;
        this.takoyaki = takoyaki;
        this.waffle = waffle;
        this.dakggochi = dakggochi;
    }

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
    }

    public String getFish() {
        return fish;
    }

    public void setFish(String fish) {
        this.fish = fish;
    }

    public String getTopokki() {
        return topokki;
    }

    public void setTopokki(String topokki) {
        this.topokki = topokki;
    }

    public String getEomuk() {
        return eomuk;
    }

    public void setEomuk(String eomuk) {
        this.eomuk = eomuk;
    }

    public String getSweetpotato() {
        return sweetpotato;
    }

    public void setSweetpotato(String sweetpotato) {
        this.sweetpotato = sweetpotato;
    }

    public String getToast() {
        return toast;
    }

    public void setToast(String toast) {
        this.toast = toast;
    }

    public String getTakoyaki() {
        return takoyaki;
    }

    public void setTakoyaki(String takoyaki) {
        this.takoyaki = takoyaki;
    }

    public String getWaffle() {
        return waffle;
    }

    public void setWaffle(String waffle) {
        this.waffle = waffle;
    }

    public String getDakggochi() {
        return dakggochi;
    }

    public void setDakggochi(String dakggochi) {
        this.dakggochi = dakggochi;
    }
}
