package com.adds.userInterface.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.adds.R;
import com.adds.application.DSApplication;


public class DSTextView extends TextView {

    private final String TAG = getClass().getSimpleName();

    public DSTextView(Context context) {
        super(context);
        setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }

    public DSTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context, attrs);
        setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }

    public DSTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont(context, attrs);
        setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }

    private void setFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.DSTextView);
        String customFont = a.getString(R.styleable.DSTextView_textFont);
        setFont(ctx, customFont);
        a.recycle();
    }

    public boolean setFont(Context ctx, String asset) {
        Typeface tf = null;
        try {
            tf = DSApplication.getFonts(asset);
        } catch (Exception e) {
            Log.e(TAG, "Could not get typeface: " + e.getMessage());
            return false;
        }

        setTypeface(tf);
        return true;
    }

}
