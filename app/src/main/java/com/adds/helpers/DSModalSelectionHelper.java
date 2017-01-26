package com.adds.helpers;

import android.content.Context;
import android.database.Cursor;

import com.adds.database.DSDataBaseHelper;
import com.adds.database.DSDatabaseFieldNames;
import com.adds.encryption.DSCryptographyHelper;
import com.adds.modalClasses.DSBankAccModal;
import com.adds.modalClasses.DSCardModal;
import com.adds.modalClasses.DSLoginPasswordModal;
import com.adds.modalClasses.DSOthersModal;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by Rolbin on November 23 2016.
 */

public class DSModalSelectionHelper implements DSModalFieldLabels {

    private static String dataCheckerAndEncrypter(String data, Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, IOException, UnrecoverableKeyException {
        if (data == null || data.isEmpty()) {
            return null;
        }
        return DSCryptographyHelper.encryptUserDatas(data, context);
    }

    public static void encryptBankModalData(List<String> dataList, Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, IOException, UnrecoverableKeyException {
        DSDataBaseHelper dataBaseHelper = new DSDataBaseHelper(context);

        DSBankAccModal bankAccModal = new DSBankAccModal();
        bankAccModal.setmAccountName(dataList.get(0));
        bankAccModal.setmBankName(dataList.get(1));
        bankAccModal.setmAccNo(dataCheckerAndEncrypter(dataList.get(2), context));
        bankAccModal.setmPassword(dataCheckerAndEncrypter(dataList.get(3), context));
        bankAccModal.setmIfscCode(dataList.get(4));
        bankAccModal.setmRemarks(dataList.get(5));

        dataBaseHelper.insertToTableBankAcc(bankAccModal);
    }

    public static DSBankAccModal getDecryptedBankDetails(String name, Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, IOException, UnrecoverableKeyException {
        DSDataBaseHelper dataBaseHelper = new DSDataBaseHelper(context);
        DSBankAccModal modal = new DSBankAccModal();
        Cursor cursor = dataBaseHelper.selectBankDetails(name);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                modal.setmAccountName(cursor.getString(cursor.getColumnIndex(DSDatabaseFieldNames.ACC_NAME)));
                modal.setmBankName(cursor.getString(cursor.getColumnIndex(DSDatabaseFieldNames.BANK_NAME)));
                modal.setmAccNo(DSCryptographyHelper.decryptUserDatas
                        (cursor.getString(cursor.getColumnIndex(DSDatabaseFieldNames.ACC_NO)), context));
                modal.setmPassword(DSCryptographyHelper.decryptUserDatas
                        (cursor.getString(cursor.getColumnIndex(DSDatabaseFieldNames.ACC_PASSWORD)), context));
                modal.setmIfscCode(cursor.getString(cursor.getColumnIndex(DSDatabaseFieldNames.IFSC_CODE)));
                modal.setmRemarks(cursor.getString(cursor.getColumnIndex(DSDatabaseFieldNames.REMARKS)));
            }
            cursor.close();
        }
        return modal;
    }

    public static void encryptCardData(List<String> dataList, Context context) throws IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, KeyStoreException {
        DSDataBaseHelper dataBaseHelper = new DSDataBaseHelper(context);

        DSCardModal cardModal = new DSCardModal();
        cardModal.setmCardName(dataList.get(0));
        cardModal.setmCardNo(dataCheckerAndEncrypter(dataList.get(1), context));
        cardModal.setmCardPin(dataCheckerAndEncrypter(dataList.get(2), context));
        cardModal.setmCvvCode(dataCheckerAndEncrypter(dataList.get(3), context));

        dataBaseHelper.insertToTableCard(cardModal);
    }

    public static DSCardModal getDecryptedCardDetails(String name, Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, IOException, UnrecoverableKeyException {
        DSDataBaseHelper dataBaseHelper = new DSDataBaseHelper(context);
        DSCardModal modal = new DSCardModal();
        Cursor cursor = dataBaseHelper.selectCardDetails(name);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                modal.setmCardName(cursor.getString(cursor.getColumnIndex(DSDatabaseFieldNames.CARD_NAME)));
                modal.setmCardNo(DSCryptographyHelper.decryptUserDatas(cursor.getString
                        (cursor.getColumnIndex(DSDatabaseFieldNames.CARD_NO)), context));
                modal.setmCardPin(DSCryptographyHelper.decryptUserDatas(cursor.getString
                        (cursor.getColumnIndex(DSDatabaseFieldNames.CARD_PIN)), context));
                modal.setmCvvCode(DSCryptographyHelper.decryptUserDatas(cursor.getString
                        (cursor.getColumnIndex(DSDatabaseFieldNames.CVV_CODE)), context));
            }
            cursor.close();
        }
        return modal;
    }

    public static void encryptLoginData(List<String> dataList, Context context) throws IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, KeyStoreException {
        DSDataBaseHelper dataBaseHelper = new DSDataBaseHelper(context);

        DSLoginPasswordModal loginPasswordModal = new DSLoginPasswordModal();
        loginPasswordModal.setmLoginName(dataList.get(0));
        loginPasswordModal.setmUserName(dataCheckerAndEncrypter(dataList.get(1), context));
        loginPasswordModal.setmPassword(dataCheckerAndEncrypter(dataList.get(2), context));

        dataBaseHelper.insertToTableLogin(loginPasswordModal);
    }

    public static DSLoginPasswordModal getDecryptedLoginDetails(String name, Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, IOException, UnrecoverableKeyException {
        DSDataBaseHelper dataBaseHelper = new DSDataBaseHelper(context);
        DSLoginPasswordModal modal = new DSLoginPasswordModal();
        Cursor cursor = dataBaseHelper.selectLoginDetails(name);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                modal.setmLoginName(cursor.getString(cursor.getColumnIndex(DSDatabaseFieldNames.LOGIN_NAME)));
                modal.setmUserName(DSCryptographyHelper.decryptUserDatas(cursor.getString
                        (cursor.getColumnIndex(DSDatabaseFieldNames.USERNAME)), context));
                modal.setmPassword(DSCryptographyHelper.decryptUserDatas(cursor.getString
                        (cursor.getColumnIndex(DSDatabaseFieldNames.PASSWORD)), context));
            }
            cursor.close();
        }
        return modal;
    }

    public static void encryptOtherData(List<String> dataList, Context context) throws IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, KeyStoreException {
        DSDataBaseHelper dataBaseHelper = new DSDataBaseHelper(context);

        DSOthersModal othersModal = new DSOthersModal();
        othersModal.setmName(dataList.get(0));
        othersModal.setmData(dataCheckerAndEncrypter(dataList.get(1), context));

        dataBaseHelper.insertToTableOther(othersModal);
    }

    public static DSOthersModal getDecryptedOtherDetails(String name, Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, IOException, UnrecoverableKeyException {
        DSDataBaseHelper dataBaseHelper = new DSDataBaseHelper(context);
        DSOthersModal modal = new DSOthersModal();
        Cursor cursor = dataBaseHelper.selectOtherDetails(name);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                modal.setmName(cursor.getString(cursor.getColumnIndex(DSDatabaseFieldNames.OTHER_DATA_NAME)));
                modal.setmData(DSCryptographyHelper.decryptUserDatas(cursor.getString
                        (cursor.getColumnIndex(DSDatabaseFieldNames.OTHER_DATA)), context));
            }
            cursor.close();
        }
        return modal;
    }

    public static ArrayList<String> getInputModalData(int modal) {

        ArrayList arrayList = new ArrayList();

        if (modal == 1) {
            arrayList.add(accountName);
            arrayList.add(bankName);
            arrayList.add(accNo);
            arrayList.add(accPassword);
            arrayList.add(ifscCode);
            arrayList.add(remarks);
        } else if (modal == 2) {
            arrayList.add(cardName);
            arrayList.add(cardNo);
            arrayList.add(cardPin);
            arrayList.add(cvvCode);
        } else if (modal == 3) {
            arrayList.add(loginName);
            arrayList.add(userName);
            arrayList.add(password);
        } else {
            arrayList.add(name);
            arrayList.add(data);
        }
        return arrayList;
    }
}
