package com.adds.modalClasses;

/**
 * Created by 10745 on 11/10/2016.
 */

public class DSLoginPasswordModal {
    private String mLoginName;
    private String mUserName;
    private String mPassword;

    public DSLoginPasswordModal(String mLoginName, String mUserName, String mPassword) {
        this.mLoginName = mLoginName;
        this.mUserName = mUserName;
        this.mPassword = mPassword;
    }

    public String getmLoginName() {
        return mLoginName;
    }

    public String getmUserName() {
        return mUserName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmLoginName(String mLoginName) {
        this.mLoginName = mLoginName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
