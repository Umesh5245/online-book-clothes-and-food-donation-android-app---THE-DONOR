package com.example.onlinedonation;

public class AdminNotificationClass {
    String desc;
    String name;
    String time;
    String donor;

    public void setDonor(String donor) {
        this.donor = donor;
    }

    public String getDonor() {
        return donor;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    String item;

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
