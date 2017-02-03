package com.elorri.android.expandablerecyclerview.ui;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.elorri.android.expandablerecyclerview.R;
import com.elorri.android.expandablerecyclerview.model.Ingredient;

public class IngredientViewHolder extends ChildViewHolder {

    private TextView mIngredientTextView;

    public IngredientViewHolder(View itemView) {
        super(itemView);
        mIngredientTextView = (TextView) itemView.findViewById(R.id.ingredient_textview);
    }

    public void bind(Ingredient ingredient) {
        mIngredientTextView.setText(ingredient.getName());
    }
}
