package com.elorri.android.expandablerecyclerview.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.elorri.android.expandablerecyclerview.R;
import com.elorri.android.expandablerecyclerview.data.ExpandableContract;

/**
 * Created by Elorri on 01/01/2017.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.TextViewHolder> {

    private final Context mContext;
    private Cursor mCursor;

    public class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public MainAdapter(Context context, Cursor cursor) {
        mContext=context;
        mCursor = cursor;
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TextViewHolder holder, final int position) {
        mCursor.moveToPosition(position);
        int directoryIdx = mCursor.getColumnIndex(ExpandableContract.DirectoryEntry.DISPLAY_NAME);
        final String label = mCursor.getString(directoryIdx);
        holder.textView.setText(label);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        holder.textView.getContext(), label, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor data) {
        mCursor = data;
        notifyDataSetChanged();
    }

}