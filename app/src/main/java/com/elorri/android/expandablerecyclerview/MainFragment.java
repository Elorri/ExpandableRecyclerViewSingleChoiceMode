package com.elorri.android.expandablerecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.elorri.android.expandablerecyclerview.data.ExpandableContract;

public class MainFragment extends Fragment implements View.OnClickListener {
    private MainAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Context mContext;
    private EditText mNameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MainAdapter(30);
        mRecyclerView.setAdapter(mAdapter);

        mNameView = (EditText) view.findViewById(R.id.name);
        view.findViewById(R.id.add_directory).setOnClickListener(this);
        view.findViewById(R.id.add_file).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        String name = String.valueOf(mNameView.getText());
        switch (view.getId()) {
            case R.id.add_directory: {
                mContext.getContentResolver().insert(ExpandableContract.buildDirectoryUri(name), null);
                return;
            }
            case R.id.add_file: {
                return;
            }
        }
    }
}
