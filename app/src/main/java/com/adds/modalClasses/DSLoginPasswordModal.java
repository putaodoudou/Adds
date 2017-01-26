package com.adds.modalClasses;

import java.io.Serializable;

/**
 * Created by 10745 on 11/10/2016.
 */

public class DSLoginPasswordModal implements Serializable {
    private String mLoginName;
    private String mUserName;
    private String mPassword;

    public DSLoginPasswordModal(String mLoginName, String mUserName, String mPassword) {
        this.mLoginName = mLoginName;
        this.mUserName = mUserName;
        this.mPassword = mPassword;
    }

    public DSLoginPasswordModal() {

    }

    public String getmLoginName() {
        return mLoginName;
    }

    public void setmLoginName(String mLoginName) {
        this.mLoginName = mLoginName;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
