package com.remnev.verbatoriamini.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.provider.BaseColumns;

import com.remnev.verbatoriamini.activities.SplashActivity;
import com.remnev.verbatoriamini.model.ExcelEvent;

import java.io.File;

/**
 * Created by nikitaremnev on 14.03.16.
 */
public class BCIDatabase extends SQLiteOpenHelper implements BaseColumns {


    private static BCIDatabase mInstance;
    private static SQLiteDatabase myWritableDb;
    private static final String DATABASE_NAME = "bci.db";

    private static final int DATABASE_VERSION = 1;

    public static final String CHILD_ID = "childId";

    //BCI Attention
    public static final String BCI_ATTENTION_TABLE_NAME = "bci_attention";
    public static final String TIMESTAMP = "timestamp";
    public static final String ATTENTION = "attention";

    //BCI Other
    public static final String BCI_EEG_TABLE_NAME = "bci_eeg";
    public static final String DELTA = "delta";
    public static final String THETA = "theta";
    public static final String LOW_ALPHA = "lowAlpha";
    public static final String HIGH_ALPHA = "highAlpha";
    public static final String LOW_BETA = "lowBeta";
    public static final String HIGH_BETA = "highBeta";
    public static final String LOW_GAMMA = "lowGamma";
    public static final String MID_GAMMA = "midGamma";

    //BCI Mediation
    public static final String BCI_MEDIATION_TABLE_NAME  = "bci_mediation";
    public static final String MEDIATION = "mediation";

    //BCI Raw
    private static final String BCI_RAW_TABLE_NAME  = "bci_raw";
    private static final String RAW = "raw";


    private static final String BCI_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + BCI_ATTENTION_TABLE_NAME + " ("
            + BCIDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TIMESTAMP + " INTEGER, " +
            ATTENTION + " INTEGER, " +
            CHILD_ID + " TEXT);";

    private static final String BCI_OTHER_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + BCI_EEG_TABLE_NAME + " ("
            + BCIDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TIMESTAMP + " INTEGER, " +
            DELTA + " INTEGER, " +
            THETA + " INTEGER, " +
            LOW_ALPHA + " INTEGER, " +
            HIGH_ALPHA + " INTEGER, " +
            LOW_BETA + " INTEGER, " +
            HIGH_BETA + " INTEGER, " +
            LOW_GAMMA + " INTEGER, " +
            MID_GAMMA + " INTEGER, " +
            CHILD_ID + " TEXT);";

    private static final String BCI_MEDIATION_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + BCI_MEDIATION_TABLE_NAME + " ("
            + BCIDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TIMESTAMP + " INTEGER, " +
            MEDIATION + " INTEGER, " +
            CHILD_ID + " TEXT);";

    private static final String BCI_RAW_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + BCI_RAW_TABLE_NAME + " ("
            + BCIDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TIMESTAMP + " INTEGER, " +
            RAW + " INTEGER, " +
            CHILD_ID + " TEXT);";

    private BCIDatabase(Context context) {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + SplashActivity.FILES_DIR + File.separator +
                DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(BCI_SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(BCI_OTHER_SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(BCI_MEDIATION_SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(BCI_RAW_SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        if (i != i2) {
            onCreate(sqLiteDatabase);
        }
    }

    @Override
    public void close() {
        super.close();
        if (myWritableDb != null) {
            myWritableDb.close();
            myWritableDb = null;
        }
    }

    public static SQLiteDatabase getMyWritableDatabase(Context mContext) {
        if ((myWritableDb == null) || (!myWritableDb.isOpen())) {
            myWritableDb = getInstance(mContext).getWritableDatabase();
        }
        return myWritableDb;
    }

    public static BCIDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BCIDatabase(context);
        }
        return mInstance;
    }

    public static void addBCIAttentionToDatabase(Context context, long timestamp, int attention) {
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            ContentValues cv = new ContentValues();
            cv.put(BCIDatabase.TIMESTAMP, timestamp);
            cv.put(BCIDatabase.ATTENTION, attention);
            cv.put(BCIDatabase.CHILD_ID, ExcelEvent.childID);
            sqdb.insert(BCIDatabase.BCI_ATTENTION_TABLE_NAME, null, cv);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }
    }

    public static void addBCITGEEGPowerToDatabase(Context context,
              int delta,
             int theta,
             int lowAlpha,
             int highAlpha,
             int lowBeta,
             int highBeta,
             int lowGamma,
             int midGamma, long timestamp) {
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            ContentValues cv = new ContentValues();
            cv.put(BCIDatabase.TIMESTAMP, timestamp);
            cv.put(BCIDatabase.DELTA, delta);
            cv.put(BCIDatabase.THETA, theta);
            cv.put(BCIDatabase.LOW_ALPHA, lowAlpha);
            cv.put(BCIDatabase.HIGH_ALPHA, highAlpha);
            cv.put(BCIDatabase.LOW_BETA, lowBeta);
            cv.put(BCIDatabase.HIGH_BETA, highBeta);
            cv.put(BCIDatabase.LOW_GAMMA, lowGamma);
            cv.put(BCIDatabase.MID_GAMMA, midGamma);
            cv.put(BCIDatabase.CHILD_ID, ExcelEvent.childID);
            sqdb.insert(BCIDatabase.BCI_EEG_TABLE_NAME, null, cv);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }
    }

    public static void addBCIMediationToDatabase(Context context, long timestamp, int mediation) {
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            ContentValues cv = new ContentValues();
            cv.put(BCIDatabase.TIMESTAMP, timestamp);
            cv.put(BCIDatabase.MEDIATION, mediation);
            cv.put(BCIDatabase.CHILD_ID, ExcelEvent.childID);
            sqdb.insert(BCIDatabase.BCI_MEDIATION_TABLE_NAME, null, cv);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }
    }

    public static void addBCIRawToDatabase(Context context, long timestamp, int raw) {
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            ContentValues cv = new ContentValues();
            cv.put(BCIDatabase.TIMESTAMP, timestamp);
            cv.put(BCIDatabase.RAW, raw);
            cv.put(BCIDatabase.CHILD_ID, ExcelEvent.childID);
            sqdb.insert(BCIDatabase.BCI_RAW_TABLE_NAME, null, cv);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }
    }

    public static void removeAll(Context mContext) {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        BCIDatabase sqh = BCIDatabase.getInstance(mContext);
        SQLiteDatabase sqdb = sqh.getMyWritableDatabase(mContext);
        try {
            sqdb.delete(BCI_MEDIATION_TABLE_NAME, null, null);
            sqdb.delete(BCI_EEG_TABLE_NAME, null, null);
            sqdb.delete(BCI_ATTENTION_TABLE_NAME, null, null);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        } finally {
            sqh.close();
            sqdb.close();
        }
    }

}
