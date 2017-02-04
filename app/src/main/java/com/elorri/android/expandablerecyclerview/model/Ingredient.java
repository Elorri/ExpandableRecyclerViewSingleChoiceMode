package com.elorri.android.expandablerecyclerview.model;

/**
 * Created by Elorri on 03/02/2017.
 */
public class Ingredient {
    private String mName;
    private int mColorIdx;

    public Ingredient(String name, int colorIdx) {
        mName=name;
        mColorIdx=colorIdx;
    }
    public String getName() {
        return mName;
    }

    public int getColorIdx() {
        return mColorIdx;
    }
}
