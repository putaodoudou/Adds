package com.adds.modalClasses;

import java.io.Serializable;

/**
 * Created by 10745 on 11/10/2016.
 */

public class DSOthersModal implements Serializable {
    private String mName;
    private String mData;

    public DSOthersModal(String mName, String mData) {
        this.mName = mName;
        this.mData = mData;
    }

    public DSOthersModal() {

    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }
}
