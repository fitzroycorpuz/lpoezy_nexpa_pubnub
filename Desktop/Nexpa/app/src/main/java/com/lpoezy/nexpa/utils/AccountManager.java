package com.lpoezy.nexpa.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pksimpson on 15/06/2016.
 */
public class AccountManager {

    private static final String NEXPA = L.TAG;
    private static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    private final Context mContext;

    public boolean isLoggedin() {

        SharedPreferences pref = mContext.getSharedPreferences(NEXPA, Context.MODE_PRIVATE);
        boolean isLoggedin = pref.getBoolean(IS_LOGGED_IN, false);
        return isLoggedin;
    }

    public void setLoggedin(boolean loggedin) {

        SharedPreferences pref = mContext.getSharedPreferences(NEXPA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_LOGGED_IN, loggedin);
        editor.commit();
    }



    public AccountManager(Context c){

        mContext = c;
    }




}
