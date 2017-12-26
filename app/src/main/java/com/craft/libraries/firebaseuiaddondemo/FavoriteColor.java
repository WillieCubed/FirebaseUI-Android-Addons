package com.craft.libraries.firebaseuiaddondemo;

public class FavoriteColor {

    private String color;
    private String longName;

    public FavoriteColor() {
        // Default constructor for FirebaseDatabase serialization
    }

    public FavoriteColor(String color, String longName) {
        this.color = color;
        this.longName = longName;
    }

    public String getColor() {
        return color;
    }

    public String getLongName() {
        return longName;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }
}
