package com.elorri.android.expandablerecyclerview.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.elorri.android.expandablerecyclerview.AppApplication;
import com.elorri.android.expandablerecyclerview.data.ExpandableContract;

public class AppService extends IntentService {

    public static final String APP_SERVICE_MESSAGE_EVENT
            = AppApplication.AUTHORITY + ".APP_SERVICE_MESSAGE_EVENT";
    public static final String ADD_FILE_REQUEST
            = AppApplication.AUTHORITY + ".action.ADD_FILE_REQUEST";
    public static final String ADD_FILE_DONE
            = AppApplication.AUTHORITY + ".action.ADD_FILE_DONE";
    public static final String DELETE_FILE_REQUEST
            = AppApplication.AUTHORITY + ".action.DELETE_FILE_REQUEST";
    public static final String DELETE_FILE_DONE
            = AppApplication.AUTHORITY + ".action.DELETE_FILE_DONE";
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
            String directoryName = intent.getExtras().getString(ExpandableContract.FileEntry
                    .DIRECTORY_NAME);
            String fileName = intent.getExtras().getString(ExpandableContract.FileEntry.FILE_NAME);
            Log.e("App", Thread.currentThread().getStackTrace()[2] + "directoryName " + "" + directoryName + " fileName " + fileName);
            Uri uri = fileName == null ?
                    ExpandableContract.FileEntry.buildDirectoryUri(directoryName) :
                    ExpandableContract.FileEntry.buildFileUri(directoryName, fileName);
            Intent intentEvent;
            int isDeleted;

            switch (intent.getAction()) {
                case ADD_FILE_REQUEST:
                    Log.e("App", Thread.currentThread().getStackTrace()[2] + "ADD_FILE_REQUEST " +
                            ""+uri);
                    //Add directory in background
                    mContext.getContentResolver().insert(uri, null);
                    //Tell anyone interested directory has been added
                    intentEvent = new Intent(APP_SERVICE_MESSAGE_EVENT);
                    intentEvent.putExtra(APP_SERVICE_MESSAGE_EVENT, ADD_FILE_DONE);
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intentEvent);
                    return;
                case DELETE_FILE_REQUEST:
                    Log.e("App", Thread.currentThread().getStackTrace()[2] + "");
                    //Delete directory in background
                    isDeleted = mContext.getContentResolver().delete(uri, null, null);
                    //Tell anyone interested directory has been deleted
                    Log.e("App", Thread.currentThread().getStackTrace()[2] + "isDeleted " + isDeleted);
                    if (isDeleted == 1) {
                        intentEvent = new Intent(APP_SERVICE_MESSAGE_EVENT);
                        intentEvent.putExtra(APP_SERVICE_MESSAGE_EVENT, DELETE_FILE_DONE);
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intentEvent);
                    }
                    return;
                default:
                    break;

            }
        }
    }
}