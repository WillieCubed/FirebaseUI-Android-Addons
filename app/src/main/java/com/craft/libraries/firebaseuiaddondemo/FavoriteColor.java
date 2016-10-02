package com.craft.libraries.firebaseuiaddondemo;

public class FavoriteColor {

    private String mColor;
    private String mLongName;

    public FavoriteColor() {
        // Default constructor for FirebaseDatabase serialization
    }

    public FavoriteColor(String shortName, String longName) {
        mColor = shortName;
        mLongName = longName;
    }

    public String getName() {
        return mColor;
    }

    public String getLongName() {
        return mLongName;
    }
}
