// Copyright MyScript. All rights reserved.

package com.elorri.android.expandablerecyclerview.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * The ItemChoiceManager. Right now this class support only SINGLE_CHOICE_MODE.
 */
public class ItemChoiceManager
{
  private static final String SELECTED_POSITION = "SELECTED_POSITION";

  Map<Integer, RecyclerView.ViewHolder> holders = new HashMap<>();
  private int mLastSavedInstancePosition;
  private boolean mNeedRestore = false;


  public void onClick(final RecyclerView.ViewHolder holder)
  {
    //Select new position. We wait a little bit (200ms) to let the user get a chance of seeing the ripple effect
    // and then we set the view as activated. This will set the new trigger inflation of background drawable mentioned as 'state_activated' in selectors.
    int milliseconds = 200;
    new CountDownTimer(milliseconds, milliseconds)
    {
      public void onTick(long millisUntilFinished)
      {
      }

      public void onFinish()
      {
        Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "onFinish ");
        holders = resetHolders(holder);
      }
    }.start();
  }

  //This method clear all old selections, remove all holders, and only keep the one given in params.
  private Map<Integer, RecyclerView.ViewHolder> resetHolders(RecyclerView.ViewHolder holder)
  {
    Map<Integer, RecyclerView.ViewHolder> holderList = new HashMap<>();
    for (int anHolderPosition : holders.keySet())
    {
      RecyclerView.ViewHolder anHolder = holders.get(anHolderPosition);
      anHolder.itemView.setActivated(false);
    }
    Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + " setActivated(true) ");
    holder.itemView.setActivated(true);
    int position = holder.getAdapterPosition();
    holderList.put(position, holder);
    return holderList;
  }

  private int getLastHolderPosition()
  {
    int size = holders.size();
    int i = 1;
    for (Integer position : holders.keySet())
    {
      if (i == size)
      {
        return holders.get(position).getAdapterPosition();
      }
      i++;
    }
    return -1;
  }

  public void onSaveInstanceState(Bundle outState)
  {
    mLastSavedInstancePosition = getLastHolderPosition();
    Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "position " + mLastSavedInstancePosition);
    outState.putInt(SELECTED_POSITION, mLastSavedInstancePosition);
  }

  public void onRestoreInstanceState(Bundle savedInstanceState)
  {
    if (savedInstanceState != null)
    {
      mNeedRestore = true;
      mLastSavedInstancePosition = savedInstanceState.getInt(SELECTED_POSITION);
      Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "position " + mLastSavedInstancePosition);
    }
  }

  public void unselect(RecyclerView.ViewHolder holder)
  {
    Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "position " + holder.getAdapterPosition());
    Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "unactivate view " + holder.itemView);
    holder.itemView.setActivated(false);
  }

  /**
   * This method allow us to get a reference to the selected viewholder
   *
   * @param holder
   */
  public void bind(RecyclerView.ViewHolder holder)
  {
    int position = holder.getAdapterPosition();
    if (mNeedRestore && position == mLastSavedInstancePosition)
    {
      holders.put(position, holder);
      //holder.itemView.setActivated(true);
      resetHolders(holder);
      Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "this " + this + " set mSelectedHolder " + holder.itemView);
      mNeedRestore = false;
    }
  }
}