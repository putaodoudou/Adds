package com.adds.userInterface.uiHelper;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.adds.application.DSApplication;

import java.util.regex.Pattern;

/**
 * Created by Rolbin on November 19 2016.
 */

public class DSUiUtils {

    public static int WIDGET_HEIGHT = 0;

    private static String TAG = DSUiUtils.class.getName();

    /**
     * get Display width in pixels
     *
     * @return
     */
    public static int getDisplayWidth() {
        Context ctx = DSApplication.getInstance().getApplicationContext();
        if (ctx == null) {
            return 0;
        }
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    /**
     * Get display ratio. Usually a ratio of 1.3 (4:3) or 1.6 (16:10) is
     * expected in case of landscape orientation. In case of portrait
     * orientation check for values like 0.75 (3:4) or 0.625 (10:16).
     *
     * @return ratio
     */
    public static double getDisplayRatio() {
        double width = getDisplayWidth();
        double height = getDisplayHeight();

        // in case of division by zero we use a try block
        try {
            double ratio = width / height;
            return ratio;
        } catch (Exception e) {
            // assume default of 16 / 10 ratio
            return 1.6;
        }
    }

    /**
     * Convert dp to pixel value
     */
    public static float dpToPixel(float dp, Context ctx) {
        return dp * ctx.getResources().getDisplayMetrics().density;
    }

    /**
     * get Display Height in pixels
     *
     * @return
     */

    public static int getDisplayHeight() {
        Context ctx = DSApplication.getInstance().getApplicationContext();
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    /**
     * Returns the height of the ActionBar in px if available, 0 else
     *
     * @param activityContext
     * @return height of ActionBar in px
     */
    public static int getActionBarHeight(Context activityContext) {
        int height = 0;
        TypedValue tv = new TypedValue();
        if (activityContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            height = TypedValue.complexToDimensionPixelSize(tv.data, activityContext.getResources().getDisplayMetrics());
        }
        return height;
    }

    /**
     * Returns the height of the StatusBar in px if available, 0 else
     *
     * @param activityContext
     * @return height of StatusBar in px
     */
    public static int getStatusBarHeight(Context activityContext) {
        int height = 0;
        int resourceId = activityContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = activityContext.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * used to reverse a specified transaction
     *
     * @param manager
     * @param name
     */
    public static void popBackStack(FragmentManager manager, String name) {
        manager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static void showStack(FragmentManager manager) {

        int backStackEntryCount = manager.getBackStackEntryCount();

        for (int cnt = 0; cnt < backStackEntryCount; cnt++) {
            FragmentManager.BackStackEntry backEntry = manager.getBackStackEntryAt(cnt);
        }
    }


    public static void replaceAndAddToBackstack(FragmentManager mgr, int resId, Fragment fragment, String stackName) {
        mgr.beginTransaction().replace(resId, fragment)
                .addToBackStack(stackName).commit();
    }


    public static void clearStack(FragmentManager manager) {
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    /**
     * used to replace a fragment using the specified resourceId
     *
     * @param manager
     * @param resourceId
     * @param fragment
     * @param tag
     */
    public static void replaceFragment(FragmentManager manager, int resourceId, Fragment fragment, String tag) {
        manager.beginTransaction().replace(resourceId, fragment, tag).commit();
    }

    /**
     * used to replace a fragment using the specified resourceId & tag will be
     * the fragment class name
     *
     * @param manager
     * @param resourceId
     * @param fragment
     */
    public static void replaceFragment(FragmentManager manager, int resourceId, Fragment fragment) {
        manager.beginTransaction().replace(resourceId, fragment, fragment.getClass().getSimpleName()).commit();
    }

    /**
     * used to get the width and height of the view by forcefully calling
     * measure method.
     *
     * @param view
     * @return array of integer containing width(0 index) and height(1 index)
     */
    public static int[] getMeasuredDimensions(View view) {

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final int[] dimens = new int[2];
        dimens[0] = view.getMeasuredWidth();
        dimens[1] = view.getMeasuredHeight();

        return dimens;
    }

    /**
     * used to get the width of the view by forcefully calling measure method.
     *
     * @param view
     * @return width of the view
     */
    public static int getMeasuredWidth(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    /**
     * used to get the height of the view by forcefully calling measure method.
     *
     * @param view
     * @return height of the view
     */
    public static int getMeasuredHeight(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredHeight();
    }

    /**
     * Calculates the total height of all items in passed listView. This
     * function is performance costly and should be used sparingly
     *
     * @param listView - listView that should be examined
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * get widget height excluding action bar and status bar
     */
    public static int getWidgetHeight() {
        // int calculateH = (UiUtility.getDisplayHeight() -
        // UiUtility.getStatusBarHeight(ApplicationContextHolder.getInstance().getApplicationContext())
        // - UiUtility
        // .getActionBarHeight(ApplicationContextHolder.getInstance().getApplicationContext()));
        return WIDGET_HEIGHT;
    }

    /**
     * used to add fragments.
     *
     * @param fm
     * @param contentId
     * @param fragments
     */
    public static void addFragments(FragmentManager fm, int contentId, Fragment... fragments) {
        if (fm == null) {
            return;
        }
        final FragmentTransaction transaction = fm.beginTransaction();
        for (Fragment f : fragments) {
            transaction.add(contentId, f);
        }
        transaction.commit();

    }

    /**
     * used to add fragment.
     *
     * @param fm
     * @param contentId
     * @param fragment
     */
    public static void addFragment(FragmentManager fm, int contentId, Fragment fragment) {
        if (fm == null) {
            return;
        }
        final FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(contentId, fragment);
        transaction.commit();
    }

    /**
     * Used to enable or disable the views
     *
     * @param enable
     * @param views
     */
    public static void enableViews(boolean enable, View... views) {
        for (View view : views) {
            view.setEnabled(enable);
        }
    }

    /**
     * Changes the background of EditText v based on isError. If isError is
     * true, background will be with red border. Request focus to the one where error has happened.
     *
     * @param v
     * @param isError
     * @return isError for the ease of some comparisons
     */
    public static boolean changeEditTextBg(View v, boolean isError) {
        if (isError) {
//            v.setBackgroundResource(R.drawable.edit_text_red_border);
            v.requestFocus();
        } else {
//            v.setBackgroundResource(R.drawable.edit_text);
        }

        return isError;
    }

    /**
     * Changes the background of EditText v based on isError. If isError is
     * true, background will be with red border and doesnt get the focus
     *
     * @param v
     * @param isError
     * @return isError for the ease of some comparisons
     */
    public static boolean changeEditTextBgWithoutFocus(View v, boolean isError) {
        if (isError) {
//            v.setBackgroundResource(R.drawable.edit_text_red_border);
        } else {
//            v.setBackgroundResource(R.drawable.edit_text);
        }

        return isError;
    }


//	/**
//	 * Using according to Material Design...
//	 * Changes the background of EditText v based on isError. If isError is
//	 * true, background will be with red line and doesnt get the focus
//	 *
//	 * @param v
//	 * @param isError
//	 * @return isError for the ease of some comparisons
//	 */
//	public static boolean changeEditTextBgWithoutFocusMaterial(View v, boolean isError) {
//
//		if (isError) {
//			v.setBackgroundResource(R.drawable.edit_text_red_line);
//		} else {
//			v.setBackgroundResource(R.drawable.edit_text_material);
//		}
//
//		return isError;
//	}

    /**
     * Change focus to the given EditText if the second argument is 'FALSE'
     *
     * @param editText
     * @param dontFocus
     * @return return 'dontFocus' for the ease of some comparisons
     */
    public static boolean requestFocus(EditText editText, boolean dontFocus) {
        if (!dontFocus) {
            editText.requestFocus();
        }
        return dontFocus;
    }

    /**
     * Check whether the given edit text contains the given number of letters.
     * If not it will change its background. Focusing on the field where error happened.
     *
     * @param editText   EditText to be validated
     * @param minLetters Minimum character count
     * @return whether it is valid or not
     */
    public static boolean validateEditText(EditText editText, int minLetters) {
        return !changeEditTextBg(editText, editText.getText().toString().trim().length() < minLetters);
    }

    /**
     * Check whether the given edit text contains the given number of letters.
     * If not it will change its background.without focusing on the view
     *
     * @param editText   EditText to be validated
     * @param minLetters Minimum character count
     * @return whether it is valid or not
     */
    public static boolean validateEditTextWithoutFocus(EditText editText, int minLetters) {
        return !changeEditTextBgWithoutFocus(editText, editText.getText().toString().trim().length() < minLetters);
    }


    /**
     * Checks whether the given email address is valid, based on a regular
     * expression. Note that this is not 100% correct, there may be a few valid
     * email addresses which this regular expression can't detect. Also, this
     * method checks for format only, there is no guarantee on the actual
     * existence of the given email address.
     *
     * @param mEmailId Email address to be validated
     * @return whether the given email address is valid or not
     */
    public static boolean validateEmailAddress(String mEmailId) {
        return Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$").matcher(mEmailId).matches();
    }

    /**
     * Checks whether the given EditText contains a valid email address(based on
     * a regular expression), if not, changes the border of this EditText to red
     * colour. Note that this is not 100% correct, there may be a few valid
     * email addresses which this regular expression can't detect and will treat
     * them as wrong address. Also, this method checks for format only, there is
     * no guarantee on the actual existence of the given email address.
     *
     * @param mEditText - EditText with email address to be validated
     * @return whether the given EditText contains a valid Email address or not
     */
    public static boolean validateEmailEditText(EditText mEditText) {
        boolean mResult = validateEmailAddress(mEditText.getText().toString());
        changeEditTextBg(mEditText, !mResult);
        return mResult;
    }

    /**
     * used to set the type face for the textView and its child classes
     *
     * @param textViews
     */
    public static void setDefaultTypeface(TextView... textViews) {
        for (TextView textView : textViews) {
            if (textView != null) {
                textView.setTypeface(DSApplication.getDefaultTypeface());
            }
        }
    }

    /**
     * Splits the given string into 2 lines, after the 2nd word, if there are
     * more than 2 words
     */
    public static final String stupidStringBreakAfterSecondBlank(String text) {
        if (text == null) {
            return text;
        }

        StringBuilder b = new StringBuilder();
        String[] words = text.split(" ");
        if ((words != null) && (words.length > 2)) {
            b.append(words[0]).append(" ").append(words[1]).append("\r\n");
            for (int i = 2; i < words.length; i++) {
                b.append(words[i]);
                if (i < (words.length - 1)) {
                    b.append(" ");
                }
            }
        }
        return b.toString().isEmpty() ? text : b.toString();
    }

    /**
     * used to hide the view and it doesn't take any space for layout purposes
     *
     * @param viewsToHide
     */
    public static void hideView(View... viewsToHide) {
        for (View view : viewsToHide) {
            if (view != null) {
                view.setVisibility(View.GONE);
            }
        }
    }

    /**
     * used to show the view
     *
     * @param viewsToShow
     */
    public static void showView(View... viewsToShow) {
        for (View view : viewsToShow) {
            if (view != null) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Used to hide the view and it take space for layout purposes
     *
     * @param view
     */
    public static void makeViewInvisible(View view) {
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Replacing German Symbols with the corresponding strings
     */

    public static String replaceGermanUmlauts(String symbol) {
        String replacedString = symbol;
        replacedString = replacedString.replace("ä", "ae");
        replacedString = replacedString.replace("ü", "ue");
        replacedString = replacedString.replace("ö", "oe");
        replacedString = replacedString.replace("Ä", "Ae");
        replacedString = replacedString.replace("Ö", "Oe");
        replacedString = replacedString.replace("Ü", "Ue");
        replacedString = replacedString.replace("ß", "ss");
        replacedString = replacedString.replace("\n", " ");
        return replacedString;
    }

    /**
     * Colours the given imageView with the given color code
     */
    public static void colourImageView(ImageView imageView, int color) {
        if (imageView != null) {
            imageView.setColorFilter(color);
        }
    }

    /**
     * Colours the given drawables with the given color code
     * <p/>
     * Why mutate is used refer :-http://www.curious-creature.com/2009/05/02/drawable-mutations/
     */
    public static Drawable[] colourDrawables(Drawable[] drawables, int color) {
        if (drawables != null) {
            for (Drawable drawable : drawables) {
                if (drawable != null) {
                    drawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                }
            }
        }

        return drawables;
    }

    /**
     * Colours the given drawable with the given color code
     * <p/>
     * Why mutate is used refer :-http://www.curious-creature.com/2009/05/02/drawable-mutations/
     */
    public static Drawable colourDrawable(Drawable drawable, int color) {
        if (drawable != null) {
            drawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }

        return drawable;
    }

    /**
     * Colours the given drawable with the given color code
     * <p/>
     * Why mutate is used refer :-http://www.curious-creature.com/2009/05/02/drawable-mutations/
     *
     * @param context
     * @param drawableResID
     * @param colorResId
     * @return
     */
    public static Drawable colourDrawable(Context context, int drawableResID, int colorResId) {
        Drawable drawable = null;
        int color = 0;
        if (context != null) {
            drawable = ContextCompat.getDrawable(context, drawableResID);
            color = ContextCompat.getColor(context, colorResId);
        }
        if (drawable != null) {
            drawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }

        return drawable;
    }

    /**
     * Determins orientation of passed activity.
     *
     * @param context, the activity
     * @return 0 if portrait, 1 else
     */
    public static int getScreenOrientation(Context context) {
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return 0;
        }
        return 1;
    }


    public static String getTextOrNull(TextView tv) {
        if (tv != null && tv.getText() != null) {
            return tv.getText().toString().trim();
        }
        return null;
    }

    public static String getText(TextView tv) {
        if (tv != null && tv.getText() != null) {
            return tv.getText().toString().trim();
        }
        return "";
    }


    public static String replaceNullWithNA(String str) {
        if (str != null) {
            return str;
        }
        return "N.A";
    }


    /**
     * Used to set focus of a edittext.
     *
     * @param editText
     * @param focusable
     */
    public static void setFocusable(EditText editText, boolean focusable) {
        if (editText != null) {
            editText.setFocusable(focusable);
            editText.setFocusableInTouchMode(focusable);
        }
    }


    public static void setEmptyText(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (editText != null) {
                editText.setText("");
            }
        }
    }

    public static void setBackground(int resId, EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (editText != null) {
                editText.setBackgroundResource(resId);
            }
        }
    }
}

