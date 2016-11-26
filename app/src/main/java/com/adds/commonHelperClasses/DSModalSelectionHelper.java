package com.adds.commonHelperClasses;

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
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by Rolbin on November 23 2016.
 */

public class DSModalSelectionHelper {

    private static String dataCheckerAndEncrypter(String data, Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, IOException {
        if (data != null && !data.isEmpty()) {
            return DSCryptographyHelper.encryptUserDatas(data, context);
        }
        return "N.A";
    }

    public static void encryptBankModalData(List<String> dataList, Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, IOException {
        DSDataBaseHelper dataBaseHelper = new DSDataBaseHelper(context);

        DSBankAccModal bankAccModal = new DSBankAccModal();
        bankAccModal.setmAccountName("rol");
        bankAccModal.setmBankName(dataCheckerAndEncrypter(dataList.get(1), context));
        bankAccModal.setmAccNo(dataCheckerAndEncrypter(dataList.get(2), context));
        bankAccModal.setmPassword(dataCheckerAndEncrypter(dataList.get(3), context));
        bankAccModal.setmIfscCode(dataCheckerAndEncrypter(dataList.get(4), context));
        bankAccModal.setmRemarks("sssn");

        dataBaseHelper.insertToTableBankAcc(bankAccModal);
        Cursor cursor = dataBaseHelper.selectBankDetails("rol");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    DSBankAccModal items = new DSBankAccModal();
                    try {
                        DSCryptographyHelper.decryptUserDatas(cursor.getString(cursor.getColumnIndex(DSDatabaseFieldNames.BANK_NAME)), context);
                    } catch (UnrecoverableKeyException e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }

    public static void getBankDetails() {

    }
}
