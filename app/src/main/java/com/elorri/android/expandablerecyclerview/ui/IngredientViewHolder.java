package com.elorri.android.expandablerecyclerview.ui;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.elorri.android.expandablerecyclerview.R;
import com.elorri.android.expandablerecyclerview.model.Ingredient;

public class IngredientViewHolder extends ChildViewHolder {

    private Context mContext;
    private TextView mIngredientTextView;

    public IngredientViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mIngredientTextView = (TextView) itemView.findViewById(R.id.ingredient_textview);
    }

    public void bind(Ingredient ingredient) {
        Resources.Theme currentTheme = mContext.getTheme();
        currentTheme.applyStyle(getResTheme(ingredient.getColorIdx()), true);
        itemView.setBackground(mContext.getResources().getDrawable(
                ThemeUtils.getResDrawable(mContext, R.attr.selectableItemBackground),
                currentTheme));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ripple
            }
        });

        mIngredientTextView.setText(ingredient.getName());
    }

    private int getResTheme(int colorIdx) {
        switch (colorIdx) {
            case 0: {
                return R.style.Bleu;
            }
            case 1: {
                return R.style.Pink;
            }
            case 2: {
                return R.style.Orange;
            }
            case 3: {
                return R.style.Bleu;
            }
            default:
                return R.style.Bleu;
        }
    }
}
