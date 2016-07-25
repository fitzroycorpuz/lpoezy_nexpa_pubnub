package com.lpoezy.nexpa.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pksimpson on 15/06/2016.
 */
public class AccountManager {

    private static final String NEXPA = L.TAG;
    private static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    private static final String USER_NAME = "USER_NAME";
    private static final String EMAIL = "EMAIL";
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

    public String getUserName() {

        SharedPreferences pref = mContext.getSharedPreferences(NEXPA, Context.MODE_PRIVATE);
        return pref.getString(USER_NAME, null);
    }

    public void setUsername(String uname) {

        SharedPreferences pref = mContext.getSharedPreferences(NEXPA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(USER_NAME, uname);
        editor.commit();
    }

    public String getEmail() {

        SharedPreferences pref = mContext.getSharedPreferences(NEXPA, Context.MODE_PRIVATE);
        return pref.getString(EMAIL, null);
    }

    public void setEmail(String email) {

        SharedPreferences pref = mContext.getSharedPreferences(NEXPA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(EMAIL, email);
        editor.commit();
    }



    public AccountManager(Context c){

        mContext = c.getApplicationContext();
    }




}
