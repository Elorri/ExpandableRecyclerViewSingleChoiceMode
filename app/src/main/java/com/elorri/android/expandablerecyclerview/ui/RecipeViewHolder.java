package com.elorri.android.expandablerecyclerview.ui;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.elorri.android.expandablerecyclerview.R;
import com.elorri.android.expandablerecyclerview.model.Recipe;

public class RecipeViewHolder extends ParentViewHolder {

    private TextView mRecipeTextView;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        mRecipeTextView = (TextView) itemView.findViewById(R.id.recipe_textview);
    }

    public void bind(Recipe recipe) {
        mRecipeTextView.setText(recipe.getName());
    }
}