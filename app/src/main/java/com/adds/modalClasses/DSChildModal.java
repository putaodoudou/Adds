package com.adds.modalClasses;

import java.util.ArrayList;

/**
 * Created by 10745 on 11/10/2016.
 */

public class DSChildModal {
    private ArrayList<DSBankAccModal> mBankAccModal;
    private ArrayList<DSCardModal> mCardModal;
    private ArrayList<DSLoginPasswordModal> mLoginPasswordModal;
    private ArrayList<DSOthersModal> mOthersModal;
    private int size;

    public DSChildModal(ArrayList<DSBankAccModal> mBankAccModal, ArrayList<DSCardModal> mCardModal,
                        ArrayList<DSLoginPasswordModal> mLoginPasswordModal, ArrayList<DSOthersModal> mOthersModal) {
        this.mBankAccModal = mBankAccModal;
        this.mCardModal = mCardModal;
        this.mLoginPasswordModal = mLoginPasswordModal;
        this.mOthersModal = mOthersModal;
    }

    public ArrayList<DSBankAccModal> getmBankAccModal() {
        return mBankAccModal;
    }

    public void setmBankAccModal(ArrayList<DSBankAccModal> mBankAccModal) {
        this.mBankAccModal = mBankAccModal;
    }

    public ArrayList<DSCardModal> getmCardModal() {
        return mCardModal;
    }

    public void setmCardModal(ArrayList<DSCardModal> mCardModal) {
        this.mCardModal = mCardModal;
    }

    public ArrayList<DSLoginPasswordModal> getmLoginPasswordModal() {
        return mLoginPasswordModal;
    }

    public void setmLoginPasswordModal(ArrayList<DSLoginPasswordModal> mLoginPasswordModal) {
        this.mLoginPasswordModal = mLoginPasswordModal;
    }

    public ArrayList<DSOthersModal> getmOthersModal() {
        return mOthersModal;
    }

    public void setmOthersModal(ArrayList<DSOthersModal> mOthersModal) {
        this.mOthersModal = mOthersModal;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size){
        this.size = size;
    }
}
