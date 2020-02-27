package com.example.onlinedonation;

public class NgoDashboardClass {
    String msg;
    String desc;
    String time;
    String donor;
    String NgoName;
    String item;

    public void setItem(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setNgoName(String ngoName) {
        NgoName = ngoName;
    }

    public String getNgoName() {
        return NgoName;
    }

    public void setDonor(String donor) {
        this.donor = donor;
    }

    public String getDonor() {
        return donor;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public String getDesc() {
        return desc;
    }

    public String getTime() {
        return time;
    }
}
