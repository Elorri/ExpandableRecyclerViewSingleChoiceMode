package com.elorri.android.expandablerecyclerview.ui;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.elorri.android.expandablerecyclerview.R;
import com.elorri.android.expandablerecyclerview.data.ExpandableContract;

public class MainFragment extends Fragment implements View.OnClickListener, LoaderManager
        .LoaderCallbacks<Cursor> {
    private MainAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Context mContext;
    private EditText mNameView;
    private int RECYCLERVIEW_LOADER_ID = 1;

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
        mAdapter = new MainAdapter(mContext, null);
        mRecyclerView.setAdapter(mAdapter);

        mNameView = (EditText) view.findViewById(R.id.name);
        view.findViewById(R.id.add_directory).setOnClickListener(this);
        view.findViewById(R.id.add_file).setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(RECYCLERVIEW_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        String name = String.valueOf(mNameView.getText());
        switch (view.getId()) {
            case R.id.add_directory: {
                mContext.getContentResolver().insert(ExpandableContract.DirectoryEntry
                        .buildDirectoryUri(name), null);
                getLoaderManager().restartLoader(RECYCLERVIEW_LOADER_ID, null, this);
                return;
            }
            case R.id.add_file: {
                return;
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = ExpandableContract.buildRootUri();
        return new CursorLoader(getActivity(),
                uri,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
