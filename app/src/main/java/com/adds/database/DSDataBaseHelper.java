package com.adds.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.adds.modalClasses.DSBankAccModal;
import com.adds.modalClasses.DSCardModal;
import com.adds.modalClasses.DSLoginPasswordModal;
import com.adds.modalClasses.DSOthersModal;

/**
 * @author Rolbin
 */
public class DSDataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Adds.sqLiteDatabase";
    private static final String TAG = "DS";
    private static final String TABLE_BANK_ACC_DATA = "TblBankAcc";
    private static final String TABLE_CARD_DATA = "TblCard";
    private static final String TABLE_LOGIN_DATA = "TblLogin";
    private static final String TABLE_OTHER_DATA = "TblOther";

    private static final String CREATE_TABLE_BANK_ACC = "CREATE TABLE "
            + TABLE_BANK_ACC_DATA + "( " + DSDatabaseFieldNames.ACC_NAME + " VARCHAR PRIMARY KEY,"
            + DSDatabaseFieldNames.BANK_NAME + " VARCHAR," + DSDatabaseFieldNames.ACC_NO + " VARCHAR,"
            + DSDatabaseFieldNames.ACC_PASSWORD + " VARCHAR," + DSDatabaseFieldNames.IFSC_CODE + " VARCHAR,"
            + DSDatabaseFieldNames.REMARKS + " VARCHAR" + ")";

    private static final String CREATE_TABLE_CARD = "CREATE TABLE "
            + TABLE_CARD_DATA + "( " + DSDatabaseFieldNames.CARD_NAME + " VARCHAR PRIMARY KEY,"
            + DSDatabaseFieldNames.CARD_NO + " VARCHAR," + DSDatabaseFieldNames.CARD_PIN + " VARCHAR,"
            + DSDatabaseFieldNames.CVV_CODE + " VARCHAR" + ")";

    private static final String CREATE_TABLE_LOGIN = "CREATE TABLE "
            + TABLE_LOGIN_DATA + "( " + DSDatabaseFieldNames.LOGIN_NAME + " VARCHAR PRIMARY KEY,"
            + DSDatabaseFieldNames.USERNAME + " VARCHAR," + DSDatabaseFieldNames.PASSWORD + " VARCHAR" + ")";

    private static final String CREATE_TABLE_OTHERS = "CREATE TABLE "
            + TABLE_OTHER_DATA + "( " + DSDatabaseFieldNames.OTHER_DATA_NAME + " VARCHAR PRIMARY KEY,"
            + DSDatabaseFieldNames.OTHER_DATA + " VARCHAR" + ")";

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
    public void onCreate(SQLiteDatabase database) {
        database.beginTransaction();
        database.execSQL(CREATE_TABLE_BANK_ACC);
        database.execSQL(CREATE_TABLE_CARD);
        database.execSQL(CREATE_TABLE_LOGIN);
        database.execSQL(CREATE_TABLE_OTHERS);
        database.setTransactionSuccessful();
        database.endTransaction();
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

    public void insertToTableCard(DSCardModal modal) {
        String uniqueName = modal.getmCardName();
        String cardNo = modal.getmCardNo();
        String cardPin = modal.getmCardPin();
        String cvvCode = modal.getmCvvCode();

        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DSDatabaseFieldNames.CARD_NAME, uniqueName);
        values.put(DSDatabaseFieldNames.CARD_NO, cardNo);
        values.put(DSDatabaseFieldNames.CARD_PIN, cardPin);
        values.put(DSDatabaseFieldNames.CVV_CODE, cvvCode);

        sqLiteDatabase.insert(TABLE_CARD_DATA, null, values);
        sqLiteDatabase.close();
    }

    public void insertToTableLogin(DSLoginPasswordModal modal) {
        String uniqueName = modal.getmLoginName();
        String userName = modal.getmUserName();
        String password = modal.getmPassword();

        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DSDatabaseFieldNames.LOGIN_NAME, uniqueName);
        values.put(DSDatabaseFieldNames.USERNAME, userName);
        values.put(DSDatabaseFieldNames.PASSWORD, password);

        sqLiteDatabase.insert(TABLE_LOGIN_DATA, null, values);
        sqLiteDatabase.close();
    }

    public void insertToTableOther(DSOthersModal modal) {
        String uniqueName = modal.getmName();
        String data = modal.getmData();

        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DSDatabaseFieldNames.OTHER_DATA_NAME, uniqueName);
        values.put(DSDatabaseFieldNames.OTHER_DATA, data);

        sqLiteDatabase.insert(TABLE_OTHER_DATA, null, values);
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

    public Cursor selectCardDetails(String uniqueName) {
        Cursor cursor;
        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        cursor = sqLiteDatabase.rawQuery("SELECT " + "* " + " FROM " + TABLE_CARD_DATA + " WHERE "
                + DSDatabaseFieldNames.CARD_NAME + " = '" + uniqueName + "' ;", null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return cursor;
    }

    public Cursor selectLoginDetails(String uniqueName) {
        Cursor cursor;
        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        cursor = sqLiteDatabase.rawQuery("SELECT " + "* " + " FROM " + TABLE_LOGIN_DATA + " WHERE "
                + DSDatabaseFieldNames.LOGIN_NAME + " = '" + uniqueName + "' ;", null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return cursor;
    }

    public Cursor selectOtherDetails(String uniqueName) {
        Cursor cursor;
        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        cursor = sqLiteDatabase.rawQuery("SELECT " + "* " + " FROM " + TABLE_OTHER_DATA + " WHERE "
                + DSDatabaseFieldNames.OTHER_DATA_NAME + " = '" + uniqueName + "' ;", null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return cursor;
    }

    public void dropAndRecreateTables() {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BANK_ACC_DATA);
        sqLiteDatabase.execSQL(CREATE_TABLE_BANK_ACC);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CARD_DATA);
        sqLiteDatabase.execSQL(TABLE_CARD_DATA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN_DATA);
        sqLiteDatabase.execSQL(TABLE_LOGIN_DATA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_OTHER_DATA);
        sqLiteDatabase.execSQL(TABLE_OTHER_DATA);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    public Cursor selectAllBankName() {
        Cursor cursor;
        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        cursor = sqLiteDatabase.rawQuery("SELECT " + DSDatabaseFieldNames.ACC_NAME + " FROM " + TABLE_BANK_ACC_DATA + " ;", null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return cursor;
    }

    public Cursor selectAllCardName() {
        Cursor cursor;
        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        cursor = sqLiteDatabase.rawQuery("SELECT " + DSDatabaseFieldNames.CARD_NAME + " FROM " + TABLE_CARD_DATA + " ;", null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return cursor;
    }

    public Cursor selectAllLoginName() {
        Cursor cursor;
        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        cursor = sqLiteDatabase.rawQuery("SELECT " + DSDatabaseFieldNames.LOGIN_NAME + " FROM " + TABLE_LOGIN_DATA + " ;", null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return cursor;
    }

    public Cursor selectAllOtherName() {
        Cursor cursor;
        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        cursor = sqLiteDatabase.rawQuery("SELECT " + DSDatabaseFieldNames.OTHER_DATA_NAME + " FROM " + TABLE_OTHER_DATA + " ;", null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return cursor;
    }
}