package com.example.bottomnavigationviewexample;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_Ngo_Requirement_RecyclerClass {

    private int imageView;
    private String NgoName;
    private String NgoLocation;
    private String NgoRequirement;

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public void setNgoName(String ngoName) {
        NgoName = ngoName;
    }

    public void setNgoLocation(String ngoLocation) {
        NgoLocation = ngoLocation;
    }

    public void setNgoRequirement(String ngoRequirement) {
        NgoRequirement = ngoRequirement;
    }

    public int getImageView() {
        return imageView;
    }

    public String getNgoName() {
        return NgoName;
    }

    public String getNgoLocation() {
        return NgoLocation;
    }

    public String getNgoRequirement() {
        return NgoRequirement;
    }
}
