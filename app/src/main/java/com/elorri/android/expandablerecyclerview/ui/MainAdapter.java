package com.elorri.android.expandablerecyclerview.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.elorri.android.expandablerecyclerview.R;
import com.elorri.android.expandablerecyclerview.model.Ingredient;
import com.elorri.android.expandablerecyclerview.model.Recipe;

import java.util.List;

public class MainAdapter extends ExpandableRecyclerAdapter<Recipe, Ingredient, RecipeViewHolder,
        IngredientViewHolder> {

    private LayoutInflater mInflater;
    private ItemSingleChoiceManager mItemChoiceManager=new ItemSingleChoiceManager();

    public MainAdapter(Context context, @NonNull List<Recipe> recipeList) {
        super(recipeList);
        mInflater = LayoutInflater.from(context);
    }

    // onCreate ...
    @Override
    public RecipeViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView = mInflater.inflate(R.layout.recipe_view, parentViewGroup, false);
        return new RecipeViewHolder(recipeView);
    }

    @Override
    public IngredientViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView = mInflater.inflate(R.layout.ingredient_view, childViewGroup, false);
        return new IngredientViewHolder(ingredientView, mItemChoiceManager);
    }

    // onBind ...
    @Override
    public void onBindParentViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int parentPosition, @NonNull Recipe recipe) {
        recipeViewHolder.bind(recipe);
    }

    @Override
    public void onBindChildViewHolder(@NonNull IngredientViewHolder ingredientViewHolder, int parentPosition, int childPosition, @NonNull Ingredient ingredient) {
        ingredientViewHolder.bind(ingredient);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState); //This will restore expand/collapsed
        // exactly like before rotation.
        mItemChoiceManager.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        mItemChoiceManager.onSaveInstanceState(savedInstanceState);
    }
}
