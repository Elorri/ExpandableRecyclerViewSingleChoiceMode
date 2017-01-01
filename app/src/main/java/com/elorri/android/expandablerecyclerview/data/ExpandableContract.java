package com.elorri.android.expandablerecyclerview.data;

import android.net.Uri;

/**
 * Created by Elorri on 01/01/2017.
 */
public class ExpandableContract {

    public static final String CONTENT_AUTHORITY = "com.elorri.android.expandablerecyclerview";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    static String PATH_APP_DIR = "appDir";
    private static String DEFAULT_DIRECTORY_NAME = "Dir_Default";

    public static Uri buildDirectoryUri(String directoryName) {
        return BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_APP_DIR)
                .appendPath(directoryName)
                .build();
    }

    public static Uri buildFileUri(String directoryName, String fileName) {
        return BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_APP_DIR)
                .appendPath(directoryName)
                .appendPath(fileName)
                .build();
    }

    public static Uri buildFileUri(String fileName) {
        return BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_APP_DIR)
                .appendPath(DEFAULT_DIRECTORY_NAME)
                .appendPath(fileName)
                .build();
    }

    public static String getDirectoryFromUri(Uri uri) {
        return uri.getPathSegments().get(1);
    }

}
