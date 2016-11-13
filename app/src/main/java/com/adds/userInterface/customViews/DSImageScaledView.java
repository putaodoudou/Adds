package com.adds.userInterface.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.adds.R;

/**
 * This class will make an image view for interface that after scale
 * its height size will change automatically
 *
 * @author Rolbin
 */
public class DSImageScaledView extends ImageView {

    public static float ScreenWidth;
    //Variable----------------------------------------------------
    private float scale = 1.0f;
    private float percent = 0;
    private float heightChangeRate;
    //Variable----------------------------------------------------

    public DSImageScaledView(final Context context) {
        super(context);
    }

    public DSImageScaledView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.ImageScaled);
        float scale = arr.getFloat(R.styleable.ImageScaled_scale, 1.0f);
        float percent = arr.getFloat(R.styleable.ImageScaled_screenPercent, 0);
        setScale(scale);
        SetSizeByScreenPercent(percent);
        arr.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final Drawable d = this.getDrawable();

        if (d != null) {

            if (getParent() instanceof HorizontalScrollView) {
                HorizontalScrollView parent = (HorizontalScrollView) getParent();
                widthMeasureSpec = parent.getWidth();
            }

            // ceil not round - avoid thin vertical gaps along the left/right edges
            final float width;
            if (percent != 0 && !isInEditMode()) {
                width = ScreenWidth * percent * getScale();
            } else {
                width = MeasureSpec.getSize(widthMeasureSpec) * getScale();
            }


            final float height = (int) Math.ceil(width * (float) d.getIntrinsicHeight() / d.getIntrinsicWidth());

            heightChangeRate = height / (float) d.getIntrinsicHeight();

            this.setMeasuredDimension((int) width, (int) height);
        } else {
            super.onMeasure((int) (widthMeasureSpec * getScale()), heightMeasureSpec);
        }
    }

    public float getHeightChangeRate() {
        return heightChangeRate;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void SetSizeByScreenPercent(float percent) {
        if (percent != 0) {
            this.percent = percent;
        }
    }
}
