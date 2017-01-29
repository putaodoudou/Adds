package com.adds.modalClasses;

import android.graphics.drawable.Drawable;

/**
 * Created by 10745 on 11/11/2016.
 */

public class DSDisplayDataModal {
    private String mData;
    private Drawable mDrawable;

    public String getData() {
        return mData;
    }

    public void setDisplayData(String data) {
        this.mData = data;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        this.mDrawable = drawable;
    }
}
