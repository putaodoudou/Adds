package com.adds.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.adds.modalClasses.DSBankAccModal;

/**
 * @author Rolbin
 */
public class DSDataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Adds.sqLiteDatabase";
    private static final String TAG = "DS";
    private static final String TABLE_BANK_ACC_DATA = "TblBankAcc";
    private static final String CREATE_TABLE_BANK_ACC = "CREATE TABLE "
            + TABLE_BANK_ACC_DATA + "( " + DSDatabaseFieldNames.ACC_NAME + " VARCHAR PRIMARY KEY,"
            + DSDatabaseFieldNames.BANK_NAME + " VARCHAR," + DSDatabaseFieldNames.ACC_NO + " VARCHAR,"
            + DSDatabaseFieldNames.ACC_PASSWORD + " VARCHAR," + DSDatabaseFieldNames.IFSC_CODE + " VARCHAR,"
            + DSDatabaseFieldNames.REMARKS + " VARCHAR" + ")";
    private Context mContext;
    private SQLiteDatabase sqLiteDatabase;

    public DSDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public DSDataBaseHelper(Context context, String name, CursorFactory factory,
                            int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db1) {
        db1.beginTransaction();
        db1.execSQL(CREATE_TABLE_BANK_ACC);
        db1.setTransactionSuccessful();
        db1.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertToTableBankAcc(DSBankAccModal modal) {
        String uniqueName = modal.getmAccountName();
        String bankName = modal.getmBankName();
        String accNo = modal.getmAccNo();
        String password = modal.getmPassword();
        String ifscCode = modal.getmIfscCode();
        String remarks = modal.getmRemarks();

        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DSDatabaseFieldNames.ACC_NAME, uniqueName);
        values.put(DSDatabaseFieldNames.BANK_NAME, bankName);
        values.put(DSDatabaseFieldNames.ACC_NO, accNo);
        values.put(DSDatabaseFieldNames.ACC_PASSWORD, password);
        values.put(DSDatabaseFieldNames.IFSC_CODE, ifscCode);
        values.put(DSDatabaseFieldNames.REMARKS, remarks);

        sqLiteDatabase.insert(TABLE_BANK_ACC_DATA, null, values);
        sqLiteDatabase.close();

    }


    public Cursor selectBankDetails(String uniqueName) {
        Cursor cursor;
        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        cursor = sqLiteDatabase.rawQuery("SELECT " + "* " + " FROM " + TABLE_BANK_ACC_DATA + " WHERE "
                + DSDatabaseFieldNames.ACC_NAME + " = '" + uniqueName + "' ;", null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return cursor;
    }

//    public Cursor selectAreasUnsort() {
//        Cursor c;
//        sqLiteDatabase = this.getReadableDatabase();
//        sqLiteDatabase.beginTransaction();
//        c = sqLiteDatabase.rawQuery("SELECT " + DSDatabaseFieldNames.TAG_AREA_NAME + " FROM " + TABLE_BANK_ACC_DATA + " ;", null);
//        sqLiteDatabase.setTransactionSuccessful();
//        sqLiteDatabase.endTransaction();
//        return c;
//    }

    public void dropAndRecreateTables() {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BANK_ACC_DATA);
        sqLiteDatabase.execSQL(CREATE_TABLE_BANK_ACC);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

}