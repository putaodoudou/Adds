package com.adds.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * ClassName : CCSharedPreferencUtils This is the common class data storage into
 * SharedPreference
 */
public class DSSharedPreferencUtils {

    private static Context context = DSApplication.getInstance();

    public static synchronized String getPref(String id, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(id, "");
    }

    public static synchronized boolean getBooleanPref(String id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(id, false);
    }

    /**
     * Gets the stored boolean value of the given id
     *
     * @param id           the id of the item to be retrieved
     * @param defaultValue returns this value if there is no stored value for the corresponding id
     */
    public static synchronized boolean getBooleanPref(String id, boolean defaultValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(id, defaultValue);
    }


    public static synchronized int getIntPref(String id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(id, 0);
    }

    public static synchronized long getLongPref(String id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(id, 0L);
    }

    public static synchronized void setPref(String id, String valStr, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(id, valStr);
        editor.commit();
    }

    public static synchronized void setBooleanPref(String id, Boolean val) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(id, val);
        editor.commit();
    }

    public static synchronized void setIntPref(String id, int val) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(id, val);
        editor.commit();
    }

    public static synchronized void setLongPref(String id, long val) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(id, val);
        editor.commit();
    }

    public static SharedPreferences getPreference() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences;
    }

}
