package com.adds.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Rolbin on November 19 2016.
 */
public class DSApplication extends Application {
    private static DSApplication INSTANCE;
    private static Typeface defaultTypeface;
    private Context mContext;

    public static synchronized DSApplication getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DSApplication();
        }
        return INSTANCE;
    }

    public static Typeface getDefaultTypeface() {
        return defaultTypeface;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (INSTANCE == null) {
            INSTANCE = new DSApplication();
        }
    }

}
