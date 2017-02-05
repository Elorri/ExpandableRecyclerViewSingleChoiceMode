package com.elorri.android.expandablerecyclerview.ui;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class ItemSingleChoiceManager {

    private CountDownTimer timer;
    private RecyclerView.ViewHolder mLastSelectedHolder;
    private int mLastSavedInstancePosition = -1;
    private boolean mNeedRestore = false; //needed to avoid selecting incorrect position when a
    // bind has been demanded (via notifydatasetchanged) but no restoration has been demanded.

    private static final String SELECTED_POSITION = "SELECTED_POSITION";

    public void onClick(final RecyclerView.ViewHolder holder) {
        Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "");
        if (timer != null) {
            timer.cancel();
        }
        //Select new position. We wait a little bit (200ms) to let the user get a chance of seeing the ripple effect
        // and then we set the view as activated. This will set the new trigger inflation of background drawable mentioned as 'state_activated' in selectors.
        int milliseconds = 800;
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
                Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + " setActivated(true) ");
            }
        }.start();
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mNeedRestore = true;
            mLastSavedInstancePosition = savedInstanceState.getInt(SELECTED_POSITION);
            //We have the position, but we need the corresponding holder to restore selection.
            // This will be done in onBind
            Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "position " + mLastSavedInstancePosition);
        }
    }

    public void bind(RecyclerView.ViewHolder holder)
    {
        int position = holder.getAdapterPosition();
        if (mNeedRestore && position == mLastSavedInstancePosition)
        {
            holder.itemView.setActivated(true);
            mLastSelectedHolder =holder;
            Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "this " + this + " set holder.itemView " + holder.itemView);
            mNeedRestore = false;
        }
    }


    public void onSaveInstanceState(Bundle savedInstanceState) {
        mLastSavedInstancePosition = mLastSelectedHolder != null ? mLastSelectedHolder
                .getAdapterPosition() : -1;
        savedInstanceState.putInt(SELECTED_POSITION, mLastSavedInstancePosition);
    }
}


