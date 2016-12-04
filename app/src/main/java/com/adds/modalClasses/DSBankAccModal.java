package com.adds.modalClasses;

/**
 * Created by 10745 on 11/10/2016.
 */

public class DSBankAccModal {
    private String mAccountName;
    private String mBankName;
    private String mPassword;
    private String mAccNo;
    private String mIfscCode;
    private String mRemarks;

    public DSBankAccModal(String mAccountName, String mBankName, String mAccNo, String mIfscCode, String mRemarks) {
        this.mAccountName = mAccountName;
        this.mBankName = mBankName;
        this.mAccNo = mAccNo;
        this.mIfscCode = mIfscCode;
        this.mRemarks = mRemarks;

    }

    public DSBankAccModal() {
    }

    public String getmAccountName() {
        return mAccountName;
    }

    public void setmAccountName(String mAccountName) {
        this.mAccountName = mAccountName;
    }

    public String getmBankName() {
        return mBankName;
    }

    public void setmBankName(String mBankName) {
        this.mBankName = mBankName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmAccNo() {
        return mAccNo;
    }

    public void setmAccNo(String mAccNo) {
        this.mAccNo = mAccNo;
    }

    public String getmIfscCode() {
        return mIfscCode;
    }

    public void setmIfscCode(String mIfscCode) {
        this.mIfscCode = mIfscCode;
    }

    public String getmRemarks() {
        return mRemarks;
    }

    public void setmRemarks(String mRemarks) {
        this.mRemarks = mRemarks;
    }
}
