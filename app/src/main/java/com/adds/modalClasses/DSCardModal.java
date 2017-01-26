package com.adds.modalClasses;

import java.io.Serializable;

/**
 * Created by 10745 on 11/10/2016.
 */

public class DSCardModal implements Serializable {
    private String mCardName;
    private String mCardNo;
    private String mCardPin;
    private String mCvvCode;

    public DSCardModal(String mCardName, String mCardNo, String mCardPin, String mCvvCode) {
        this.mCardName = mCardName;
        this.mCardNo = mCardNo;
        this.mCardPin = mCardPin;
        this.mCvvCode = mCvvCode;
    }

    public DSCardModal() {
    }

    public String getmCardName() {
        return mCardName;
    }

    public void setmCardName(String mCardName) {
        this.mCardName = mCardName;
    }

    public String getmCardNo() {
        return mCardNo;
    }

    public void setmCardNo(String mCardNo) {
        this.mCardNo = mCardNo;
    }

    public String getmCardPin() {
        return mCardPin;
    }

    public void setmCardPin(String mCardPin) {
        this.mCardPin = mCardPin;
    }

    public String getmCvvCode() {
        return mCvvCode;
    }

    public void setmCvvCode(String mCvvCode) {
        this.mCvvCode = mCvvCode;
    }
}
