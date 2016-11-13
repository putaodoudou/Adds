package com.adds.userInterface.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.adds.R;

/**
 * Custom view class created for creating rounded letter views
 *
 * @author Rolbin
 */
public class DSRoundedLetterView extends View {

    private static final int DEFAULT_VIEW_SIZE = 96;
    private static int DEFAULT_TITLE_COLOR = Color.WHITE;
    private static int DEFAULT_BACKGROUND_COLOR = Color.CYAN;
    private static float DEFAULT_TITLE_SIZE = 25f;
    private static String DEFAULT_TITLE = "A";

    private int mTitleColor = DEFAULT_TITLE_COLOR;
    private int mBackgroundColor = DEFAULT_BACKGROUND_COLOR;
    private String mTitleText = DEFAULT_TITLE;
    private float mTitleSize = DEFAULT_TITLE_SIZE;

    private TextPaint mTitleTextPaint;
    private Paint mBackgroundPaint;
    private RectF mInnerRectF;
    private int mViewSize;

    private Typeface mFont = Typeface.defaultFromStyle(Typeface.NORMAL);

    public DSRoundedLetterView(Context context) {
        super(context);
        init(null, 0);
    }

    public DSRoundedLetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DSRoundedLetterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray array = getContext().obtainStyledAttributes(
                attrs, R.styleable.RoundedLetterView, defStyle, 0);

        if (array.hasValue(R.styleable.RoundedLetterView_rlv_titleText)) {
            mTitleText = array.getString(R.styleable.RoundedLetterView_rlv_titleText);
        }

        mTitleColor = array.getColor(R.styleable.RoundedLetterView_rlv_titleColor, DEFAULT_TITLE_COLOR);
        mBackgroundColor = array.getColor(R.styleable.RoundedLetterView_rlv_backgroundColorValue, DEFAULT_BACKGROUND_COLOR);

        mTitleSize = array.getDimension(R.styleable.RoundedLetterView_rlv_titleSize, DEFAULT_TITLE_SIZE);
        array.recycle();

        //Title TextPaint
        mTitleTextPaint = new TextPaint();
        mTitleTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTitleTextPaint.setTypeface(mFont);
        mTitleTextPaint.setTextAlign(Paint.Align.CENTER);
        mTitleTextPaint.setLinearText(true);
        mTitleTextPaint.setColor(mTitleColor);
        mTitleTextPaint.setTextSize(mTitleSize);

        //Background Paint
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(mBackgroundColor);

        mInnerRectF = new RectF();
    }

    private void invalidateTextPaintsAndBackgroundPaints() {
        mTitleTextPaint.setTypeface(mFont);
        mTitleTextPaint.setTextSize(mTitleSize);
    }

    private void invalidatePaints() {
        mBackgroundPaint.setColor(mBackgroundColor);
        mTitleTextPaint.setColor(mTitleColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = resolveSize(DEFAULT_VIEW_SIZE, widthMeasureSpec);
        int height = resolveSize(DEFAULT_VIEW_SIZE, heightMeasureSpec);
        mViewSize = Math.min(width, height);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mInnerRectF.set(0, 0, mViewSize, mViewSize);
        mInnerRectF.offset((getWidth() - mViewSize) / 2, (getHeight() - mViewSize) / 2);

        float centerX = mInnerRectF.centerX();
        float centerY = mInnerRectF.centerY();

        int xPos = (int) centerX;
        int yPos = (int) (centerY - (mTitleTextPaint.descent() + mTitleTextPaint.ascent()) / 2);

        canvas.drawOval(mInnerRectF, mBackgroundPaint);

        canvas.drawText(mTitleText,
                xPos,
                yPos,
                mTitleTextPaint);
    }

    /**
     * Gets the title string attribute value.
     *
     * @return The title string attribute value.
     */
    public String getTitleText() {
        return mTitleText;
    }

    /**
     * Sets the view's title string attribute value.
     *
     * @param title The example string attribute value to use.
     */
    public void setTitleText(String title) {
        mTitleText = title;
        invalidate();
    }

    /**
     * Gets the background color attribute value.
     *
     * @return The background color attribute value.
     */
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    /**
     * Sets the view's background color attribute value.
     *
     * @param backgroundColor The background color attribute value to use.
     */
    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
        invalidatePaints();
    }

    /**
     * Sets the text background color attribute value.
     *
     * @param textColor The text color attribute value to use.
     */
    public void setTextColor(int textColor) {
        mTitleColor = textColor;
        invalidatePaints();
    }

    /**
     * Gets the title size dimension attribute value.
     *
     * @return The title size dimension attribute value.
     */
    public float getTitleSize() {
        return mTitleSize;
    }

    /**
     * Sets the view's title size dimension attribute value.
     *
     * @param titleSize The title size dimension attribute value to use.
     */
    public void setTitleSize(float titleSize) {
        mTitleSize = titleSize;
        invalidateTextPaintsAndBackgroundPaints();
    }

    /**
     * Sets the view's title typeface.
     *
     * @param font The typeface to be used for the text.
     */
    public void setTextTypeface(Typeface font) {
        this.mFont = font;
        invalidateTextPaintsAndBackgroundPaints();
    }
}
