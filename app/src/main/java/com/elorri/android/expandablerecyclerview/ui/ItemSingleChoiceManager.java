package com.elorri.android.expandablerecyclerview.ui;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;

public class ItemSingleChoiceManager {

    /* needed to set selection after the ripple effect has finished  */
    private CountDownTimer timer;

    /* needed to avoid selecting incorrect position when a bind has been demanded (via notifydatasetchanged) but no restoration has been demanded  */
    private boolean mNeedRestore = false;

    /* Needed for restoration after rotation orreduction */
    private static final String SELECTED_POSITION = "SELECTED_POSITION";
    private int mLastSavedInstancePosition = -1;
    private RecyclerView.ViewHolder mLastSelectedHolder;

    public void onClick(final RecyclerView.ViewHolder holder) {
        if (timer != null) {
            timer.cancel();
        }
        //Select new position. We wait a little bit (200ms) to let the user get a chance of seeing the ripple effect
        // and then we set the view as activated. This will set the new trigger inflation of background drawable mentioned as 'state_activated' in selectors.
        int milliseconds = 200;
        timer = new CountDownTimer(milliseconds, milliseconds) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                if (mLastSelectedHolder != null && mLastSelectedHolder != holder) {
                    mLastSelectedHolder.itemView.setActivated(false);
                }
                holder.itemView.setActivated(true);
                timer = null;
                mLastSelectedHolder = holder;
            }
        }.start();
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        /* CAUTION : to work properly adapter should save and restore its items in the same
        position. This means  :
        - every collapsed collection before rotation should be collapsed after rotation.
        - every uncollapsed collection before rotation should be uncollapsed after rotation. */
        if (savedInstanceState != null) {
            mNeedRestore = true;
            mLastSavedInstancePosition = savedInstanceState.getInt(SELECTED_POSITION);
            //We have the position, but we need the corresponding holder to restore selection.
            // This will be done in bind method
        }
    }

    public void bind(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        if (mNeedRestore && position == mLastSavedInstancePosition) {
            holder.itemView.setActivated(true);
            mLastSelectedHolder = holder;
            mNeedRestore = false;
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        /* CAUTION : to work properly adapter should save and restore its items in the same
        position. This means  :
        - every collapsed collection before rotation should be collapsed after rotation.
        - every uncollapsed collection before rotation should be uncollapsed after rotation. */
        mLastSavedInstancePosition = mLastSelectedHolder != null ? mLastSelectedHolder
                .getAdapterPosition() : -1;
        savedInstanceState.putInt(SELECTED_POSITION, mLastSavedInstancePosition);
    }
}


