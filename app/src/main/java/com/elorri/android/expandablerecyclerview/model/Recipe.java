package com.elorri.android.expandablerecyclerview.model;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

public class Recipe implements Parent<Ingredient> {

    // a recipe contains several ingredients
    private List<Ingredient> mIngredients;
    private String mName;

    public Recipe(String name, List<Ingredient> ingredients) {
        mName=name;
        mIngredients = ingredients;
    }

    @Override
    public List<Ingredient> getChildList() {
        return mIngredients;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public String getName() {
        return mName;
    }
}