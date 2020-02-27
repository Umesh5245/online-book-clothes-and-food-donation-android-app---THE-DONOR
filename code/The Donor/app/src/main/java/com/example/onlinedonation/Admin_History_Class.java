package com.example.onlinedonation;

public class Admin_History_Class {

    private String date;
    private int itemImage;
    private String donorName;

    public String getDate() {
        return date;
    }

    public int getItemImage() {
        return itemImage;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }
}
