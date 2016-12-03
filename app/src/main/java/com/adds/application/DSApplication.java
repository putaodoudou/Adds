package com.adds.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import com.adds.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rolbin on November 19 2016.
 */
public class DSApplication extends Application {
    public static final int FONT_BOLD = 0;
    public static final int FONT_REGULAR = 1;
    private static DSApplication INSTANCE;
    private static Map<String, Typeface> sTypefaces = new HashMap<>();
    private static String[] sFontNames;
    private Context mContext;

    public static synchronized DSApplication getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DSApplication();
        }
        return INSTANCE;
    }

    public static Typeface getFonts(int fontId) {
        Typeface typeFace = sTypefaces.get(sFontNames[fontId]);
        if (typeFace == null) {
            return null;
        }
        return typeFace;
    }

    public static Typeface getFonts(String fontName) {
        Typeface typeFace = sTypefaces.get(fontName);
        if (typeFace == null) {
            return null;

        }
        return typeFace;
    }

    /**
     * Used to get the default Typeface
     */
    public static Typeface getDefaultTypeface() {
        return getFonts(FONT_REGULAR);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (INSTANCE == null) {
            INSTANCE = new DSApplication();
        }
        String fontBold = this.getResources().getString(R.string.default_font_name_bold);
        String fontLight = this.getResources().getString(R.string.default_font_name);
        sFontNames = new String[]{fontBold, fontLight};
    }

    private void initFonts() {
        for (String font : sFontNames) {
            try {
                Typeface typeFace = Typeface.createFromAsset(getAssets(), font);
                if (typeFace == null) {
                    continue;
                }
                sTypefaces.put(font, typeFace);
            } catch (Exception e) {
                Log.e("CCApplication", "Failed to load some fonts");
            }
        }
    }


}
