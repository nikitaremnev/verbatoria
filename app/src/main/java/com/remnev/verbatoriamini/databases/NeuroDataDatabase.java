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
public class NeuroDataDatabase extends SQLiteOpenHelper implements BaseColumns {

    private static NeuroDataDatabase mInstance;
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
            + NeuroDataDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TIMESTAMP + " INTEGER, " +
            ATTENTION + " INTEGER, " +
            CHILD_ID + " TEXT);";

    private static final String BCI_OTHER_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + BCI_EEG_TABLE_NAME + " ("
            + NeuroDataDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
            + NeuroDataDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TIMESTAMP + " INTEGER, " +
            MEDIATION + " INTEGER, " +
            CHILD_ID + " TEXT);";

    private static final String BCI_RAW_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + BCI_RAW_TABLE_NAME + " ("
            + NeuroDataDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TIMESTAMP + " INTEGER, " +
            RAW + " INTEGER, " +
            CHILD_ID + " TEXT);";

    public static NeuroDataDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NeuroDataDatabase(context);
        }
        return mInstance;
    }

    private NeuroDataDatabase(Context context) {
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

    public static void addBCIAttentionToDatabase(Context context, long timestamp, int attention) {
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            ContentValues cv = new ContentValues();
            cv.put(NeuroDataDatabase.TIMESTAMP, timestamp);
            cv.put(NeuroDataDatabase.ATTENTION, attention);
            cv.put(NeuroDataDatabase.CHILD_ID, ExcelEvent.childID);
            sqdb.insert(NeuroDataDatabase.BCI_ATTENTION_TABLE_NAME, null, cv);
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
            cv.put(NeuroDataDatabase.TIMESTAMP, timestamp);
            cv.put(NeuroDataDatabase.DELTA, delta);
            cv.put(NeuroDataDatabase.THETA, theta);
            cv.put(NeuroDataDatabase.LOW_ALPHA, lowAlpha);
            cv.put(NeuroDataDatabase.HIGH_ALPHA, highAlpha);
            cv.put(NeuroDataDatabase.LOW_BETA, lowBeta);
            cv.put(NeuroDataDatabase.HIGH_BETA, highBeta);
            cv.put(NeuroDataDatabase.LOW_GAMMA, lowGamma);
            cv.put(NeuroDataDatabase.MID_GAMMA, midGamma);
            cv.put(NeuroDataDatabase.CHILD_ID, ExcelEvent.childID);
            sqdb.insert(NeuroDataDatabase.BCI_EEG_TABLE_NAME, null, cv);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }
    }

    public static void addBCIMediationToDatabase(Context context, long timestamp, int mediation) {
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            ContentValues cv = new ContentValues();
            cv.put(NeuroDataDatabase.TIMESTAMP, timestamp);
            cv.put(NeuroDataDatabase.MEDIATION, mediation);
            cv.put(NeuroDataDatabase.CHILD_ID, ExcelEvent.childID);
            sqdb.insert(NeuroDataDatabase.BCI_MEDIATION_TABLE_NAME, null, cv);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }
    }

    public static void removeAll(Context mContext) {
        NeuroDataDatabase sqh = NeuroDataDatabase.getInstance(mContext);
        SQLiteDatabase sqdb = getMyWritableDatabase(mContext);
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
