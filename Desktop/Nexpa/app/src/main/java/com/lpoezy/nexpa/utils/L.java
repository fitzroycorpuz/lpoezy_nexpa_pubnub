package com.lpoezy.nexpa.utils;

import android.util.Log;

/**
 * Created by pksimpson on 19/05/2016.
 */
public class L {

    public static final String TAG = "NEXPA";

    public static void debug(String msg){
        Log.d(TAG, msg);
    }

    public static void error(String msg){
        Log.e(TAG, msg);
    }
}
