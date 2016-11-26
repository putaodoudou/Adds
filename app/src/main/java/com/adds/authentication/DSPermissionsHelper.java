package com.adds.authentication;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;


/**
 * Helper class to check and request runtime permissions.
 *
 * @author Rolbin
 */
public class DSPermissionsHelper {

    public static final int REQ_LOCATION = 1;
    public static final int PERMISSION_GRANTED = 1;
    public static final int PERMISSION_DENIED = 0;

    private Activity mActivity;
    private PermissionsCallback mCallback;
    private Fragment mFragment;

    public DSPermissionsHelper(Fragment fragment, PermissionsCallback callback) {
        this.mFragment = fragment;
        this.mActivity = fragment.getActivity();
        this.mCallback = callback;
    }

    public DSPermissionsHelper(Activity activity, PermissionsCallback callback) {
        this.mActivity = activity;
        this.mCallback = callback;
    }

    public boolean hasPermisson(String... permissions) {
        boolean hasPerm = false;
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(mActivity, permission);
            hasPerm = (PackageManager.PERMISSION_GRANTED == result);
            if (!hasPerm) {
                return false;
            }
        }
        return hasPerm;
    }


    public boolean shouldShowRequestPermissionRationale(String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission);
    }


    public void requestPermission(String... permissions) {
        mFragment.requestPermissions(permissions, REQ_LOCATION);
    }

    public void requestPermissionActivity(String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mActivity.requestPermissions(permissions, REQ_LOCATION);
        }
    }

    public void handleRequestPermissionResult(int requestCode,
                                              String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQ_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                boolean granted = false;
                for (int idx = 0; idx < grantResults.length; ++idx) {
                    granted = (grantResults[idx] == PackageManager.PERMISSION_GRANTED);
                    if (!granted) {
                        break;
                    }
                }

                if (granted) {
                    mCallback.permissionGranted(permissions);
                } else {
                    mCallback.permissionDenied(permissions);
                }

            }

        }
    }

    /**
     * Callback used to handle permission request results.
     */
    public interface PermissionsCallback {
        void permissionGranted(String permissions[]);

        void permissionDenied(String permissions[]);
    }

}
