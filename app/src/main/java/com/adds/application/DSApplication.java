package com.adds.application;

import android.content.Context;

/**
 * Created by Rolbin on November 19 2016.
 */
public class DSApplication {
    private static DSApplication INSTANCE;
    private static Context mContext;

    private DSApplication() {
    }

    public static DSApplication getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DSApplication();
        }
        return INSTANCE;
    }

    public static Context getApplicationContext() {
        if (mContext == null) {
            mContext = getApplicationContext();
        }
        return mContext;
    }
}
