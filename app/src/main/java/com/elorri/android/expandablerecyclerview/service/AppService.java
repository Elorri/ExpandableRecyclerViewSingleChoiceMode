package com.elorri.android.expandablerecyclerview.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.elorri.android.expandablerecyclerview.AppApplication;
import com.elorri.android.expandablerecyclerview.data.ExpandableContract;

public class AppService extends IntentService {

    public static final String APP_SERVICE_MESSAGE_EVENT
            = AppApplication.AUTHORITY + ".APP_SERVICE_MESSAGE_EVENT";
    public static final String ADD_DIRECTORY_REQUEST
            = AppApplication.AUTHORITY + ".action.ADD_DIRECTORY_REQUEST";
    public static final String ADD_DIRECTORY_DONE
            = AppApplication.AUTHORITY + ".action.ADD_DIRECTORY_DONE";
    public static final String DELETE_DIRECTORY_REQUEST
            = AppApplication.AUTHORITY + ".action.DELETE_DIRECTORY_REQUEST";
    public static final String DELETE_DIRECTORY_DONE
            = AppApplication.AUTHORITY + ".action.DELETE_DIRECTORY_DONE";
    private Context mContext;

    public AppService() {
        super(AppService.class.getSimpleName());
        Log.e("App", Thread.currentThread().getStackTrace()[2] + "");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mContext = getApplicationContext();
        Log.e("App", Thread.currentThread().getStackTrace()[2] + "");
        if (intent != null) {
            String name;
            Intent intentEvent;
            switch (intent.getAction()) {
                case ADD_DIRECTORY_REQUEST:
                    Log.e("App", Thread.currentThread().getStackTrace()[2] + "");
                    //Add directory in background
                    name = intent.getStringExtra(ExpandableContract.DirectoryEntry.DISPLAY_NAME);
                    mContext.getContentResolver().insert(ExpandableContract.DirectoryEntry.buildDirectoryUri(name), null);
                    //Tell anyone interested directory has been added
                    intentEvent = new Intent(APP_SERVICE_MESSAGE_EVENT);
                    intentEvent.putExtra(APP_SERVICE_MESSAGE_EVENT, ADD_DIRECTORY_DONE);
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intentEvent);
                    return;
                case DELETE_DIRECTORY_REQUEST:
                    Log.e("App", Thread.currentThread().getStackTrace()[2] + "");
                    //Add directory in background
                    name = intent.getStringExtra(ExpandableContract.DirectoryEntry.DISPLAY_NAME);
                    int isDeleted = mContext.getContentResolver().delete(ExpandableContract
                            .DirectoryEntry.buildDirectoryUri(name), null, null);
                    //Tell anyone interested directory has been deleted
                    Log.e("App", Thread.currentThread().getStackTrace()[2] + "isDeleted "+isDeleted);
                    if (isDeleted == 1) {
                        intentEvent = new Intent(APP_SERVICE_MESSAGE_EVENT);
                        intentEvent.putExtra(APP_SERVICE_MESSAGE_EVENT, DELETE_DIRECTORY_DONE);
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intentEvent);
                    }
                    return;
                default:
                    return;

            }
        }
        return;
    }
}