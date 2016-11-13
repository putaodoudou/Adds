package com.adds.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.adds.modalClasses.DSBankAccModal;

public class DSDataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Adds.sqLiteDatabase";
    private static final String TAG = "DS";


    private static final String TABLE_BANK_ACC_DATA = "TblBankAcc";
    private static final String CREATE_TABLE_BANK_ACC = "CREATE TABLE "
            + TABLE_BANK_ACC_DATA + "( " + DSDatabaseFieldNames.TAG_AREA_ID + " INTEGER PRIMARY KEY,"
            + DSDatabaseFieldNames.TAG_AREA_NAME + " VARCHAR" + ")";
    private Context mContext;



//    private static final String CREATE_TABLE_BUSINESS = "CREATE TABLE "
//            + TABLE_BUSINESS + "( " + DbFieldname.TAG_BUS_ID + " INTEGER PRIMARY KEY," + DbFieldname.TAG_BUS_NAME + " VARCHAR,"
//            + DbFieldname.TAG_BUS_EMAIL + " VARCHAR,"
//            + DbFieldname.TAG_CAT_ID + " INTEGER,"
//            + DbFieldname.TAG_SUB_CAT_ID + " INTEGER,"
//            + DbFieldname.TAG_BUS_FB + " VARCHAR,"
//            + DbFieldname.TAG_BUS_TWITTER + " VARCHAR,"
//            + DbFieldname.TAG_BUS_INSTAGRAM + " VARCHAR,"
//            + DbFieldname.TAG_BUS_SPONSOR + " VARCHAR,"
//            + DbFieldname.TAG_BUS_TAGS + " VARCHAR,"
//            + DbFieldname.TAG_BUS_REG_ID + " VARCHAR,"
//            + DbFieldname.TAG_BUS_MOBILE + " VARCHAR,"
//            + DbFieldname.TAG_BUS_OFFICE + " VARCHAR ,FOREIGN KEY ("
//            + DbFieldname.TAG_CAT_ID + ") REFERENCES " +
//            TABLE_CAT + "(" + DbFieldname.TAG_CAT_ID
//            + "),FOREIGN KEY ("
//            + DbFieldname.TAG_SUB_CAT_ID + ") REFERENCES "
//            + TABLE_SUB_CAT + "(" + DbFieldname.TAG_SUB_CAT_ID
//            + "))";
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
        String ifscCode = modal.getmIfscCode();
        String remarks = modal.getmRemarks();
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DSDatabaseFieldNames.TAG_AREA_ID, uniqueName);
        values.put(DSDatabaseFieldNames.TAG_AREA_NAME, bankName);
        sqLiteDatabase.insert(TABLE_BANK_ACC_DATA, null, values);
        sqLiteDatabase.close();

    }


    public Cursor selectAreas() {
        Cursor c;
        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        c = sqLiteDatabase.rawQuery("SELECT " + DSDatabaseFieldNames.TAG_AREA_NAME +" FROM " + TABLE_BANK_ACC_DATA + " ORDER BY " + DSDatabaseFieldNames.TAG_AREA_NAME + " ;", null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return c;
    }
    public Cursor selectAreasUnsort() {
        Cursor c;
        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        c = sqLiteDatabase.rawQuery("SELECT " + DSDatabaseFieldNames.TAG_AREA_NAME +" FROM " + TABLE_BANK_ACC_DATA + " ;", null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return c;
    }

    public void dropAndRecreateTables() {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BANK_ACC_DATA);
        sqLiteDatabase.execSQL(CREATE_TABLE_BANK_ACC);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

}