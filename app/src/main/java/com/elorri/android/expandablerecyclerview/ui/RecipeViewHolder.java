package com.elorri.android.expandablerecyclerview.ui;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.elorri.android.expandablerecyclerview.R;
import com.elorri.android.expandablerecyclerview.model.Recipe;

public class RecipeViewHolder extends ParentViewHolder {

    private TextView mRecipeTextView;
    private Context mContext;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        mContext=itemView.getContext();
        mRecipeTextView = (TextView) itemView.findViewById(R.id.recipe_textview);
    }

    public void bind(Recipe recipe) {
        mContext.getTheme().applyStyle(R.style.AppTheme, true);
        mRecipeTextView.setText(recipe.getName());
    }
}