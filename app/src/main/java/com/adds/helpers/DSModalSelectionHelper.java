package com.adds.helpers;

import android.content.Context;
import android.database.Cursor;

import com.adds.database.DSDataBaseHelper;
import com.adds.database.DSDatabaseFieldNames;
import com.adds.encryption.DSCryptographyHelper;
import com.adds.modalClasses.DSBankAccModal;

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
                modal.setmRemarks(cursor.getString(cursor.getColumnIndex(DSDatabaseFieldNames.REMARKS)));
            }
        }
        cursor.close();
        return modal;
    }

    public static ArrayList<String> getInputModalData(int modal) {

        ArrayList arrayList = new ArrayList();

        if (modal == 1) {
            arrayList.add(accountName);
            arrayList.add(bankName);
            arrayList.add(accPassword);
            arrayList.add(accNo);
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
