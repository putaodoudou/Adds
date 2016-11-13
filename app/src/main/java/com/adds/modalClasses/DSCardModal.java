package com.adds.modalClasses;

/**
 * Created by 10745 on 11/10/2016.
 */

public class DSCardModal {
    private String mCardName;
    private int mCardNo;
    private int mCardPin;
    private int mCvvCode;

    public DSCardModal(String mCardName, int mCardNo, int mCardPin, int mCvvCode) {
        this.mCardName = mCardName;
        this.mCardNo = mCardNo;
        this.mCardPin = mCardPin;
        this.mCvvCode = mCvvCode;
    }

    public String getmCardName() {
        return mCardName;
    }

    public void setmCardName(String mCardName) {
        this.mCardName = mCardName;
    }

    public int getmCardNo() {
        return mCardNo;
    }

    public void setmCardNo(int mCardNo) {
        this.mCardNo = mCardNo;
    }

    public int getmCardPin() {
        return mCardPin;
    }

    public void setmCardPin(int mCardPin) {
        this.mCardPin = mCardPin;
    }

    public int getmCvvCode() {
        return mCvvCode;
    }

    public void setmCvvCode(int mCvvCode) {
        this.mCvvCode = mCvvCode;
    }
}
