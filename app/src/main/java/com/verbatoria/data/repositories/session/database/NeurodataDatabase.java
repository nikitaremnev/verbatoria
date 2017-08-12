package com.verbatoria.data.repositories.session.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.provider.BaseColumns;

import com.verbatoria.business.token.processor.TokenProcessor;
import com.verbatoria.data.repositories.session.model.AttentionMeasurement;
import com.verbatoria.data.repositories.session.model.BaseMeasurement;
import com.verbatoria.data.repositories.session.model.EEGMeasurement;
import com.verbatoria.data.repositories.session.model.MediationMeasurement;
import com.verbatoria.utils.FileUtils;
import com.verbatoria.utils.Helper;
import com.verbatoria.utils.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * База данных для записи данных с нейроинтерфейса
 *
 * @author nikitaremnev
 */
public class NeurodataDatabase extends SQLiteOpenHelper implements BaseColumns {

    private static final String TAG = NeurodataDatabase.class.getSimpleName();

    private static NeurodataDatabase sDatabaseInstance;
    private static SQLiteDatabase sWritableDatabase;

    private static final String DATABASE_NAME = "neurodata.db";
    private static final int DATABASE_VERSION = 1;

    //Внимание
    private static final String ATTENTION_TABLE_NAME = "bci_attention";
    private static final String TIMESTAMP_COLUMN_NAME = "timestamp";
    private static final String ATTENTION_COLUMN_NAME = "attention";

    //Альфа, бета, гамма, дельта, тета
    private static final String EEG_TABLE_NAME = "bci_eeg";
    private static final String DELTA_COLUMN_NAME = "delta";
    private static final String THETA_COLUMN_NAME = "theta";
    private static final String LOW_ALPHA_COLUMN_NAME = "lowAlpha";
    private static final String HIGH_ALPHA_COLUMN_NAME = "highAlpha";
    private static final String LOW_BETA_COLUMN_NAME = "lowBeta";
    private static final String HIGH_BETA_COLUMN_NAME = "highBeta";
    private static final String LOW_GAMMA_COLUMN_NAME = "lowGamma";
    private static final String MID_GAMMA_COLUMN_NAME = "midGamma";

    //Медиана
    private static final String MEDIATION_TABLE_NAME = "bci_mediation";
    private static final String MEDIATION_COLUMN_NAME = "mediation";

    private static final String ATTENTION_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + ATTENTION_TABLE_NAME + " ("
            + NeurodataDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TIMESTAMP_COLUMN_NAME + " INTEGER, " +
            ATTENTION_COLUMN_NAME + " INTEGER);";

    private static final String EEG_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + EEG_TABLE_NAME + " ("
            + NeurodataDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TIMESTAMP_COLUMN_NAME + " INTEGER, " +
            DELTA_COLUMN_NAME + " INTEGER, " +
            THETA_COLUMN_NAME + " INTEGER, " +
            LOW_ALPHA_COLUMN_NAME + " INTEGER, " +
            HIGH_ALPHA_COLUMN_NAME + " INTEGER, " +
            LOW_BETA_COLUMN_NAME + " INTEGER, " +
            HIGH_BETA_COLUMN_NAME + " INTEGER, " +
            LOW_GAMMA_COLUMN_NAME + " INTEGER, " +
            MID_GAMMA_COLUMN_NAME + " INTEGER);";

    private static final String MEDIATION_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + MEDIATION_TABLE_NAME + " ("
            + NeurodataDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TIMESTAMP_COLUMN_NAME + " INTEGER, " +
            MEDIATION_COLUMN_NAME + " INTEGER);";

    private static NeurodataDatabase getInstance(Context context) {
        if (sDatabaseInstance == null) {
            sDatabaseInstance = new NeurodataDatabase(context);
        }
        return sDatabaseInstance;
    }

    private NeurodataDatabase(Context context) {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + FileUtils.APPLICATION_FILES_DIRECTORY + File.separator +
                DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ATTENTION_SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(EEG_SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(MEDIATION_SQL_CREATE_ENTRIES);
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
        if (sWritableDatabase != null) {
            sWritableDatabase.close();
            sWritableDatabase = null;
        }
    }

    private static SQLiteDatabase getMyWritableDatabase(Context сontext) {
        if ((sWritableDatabase == null) || (!sWritableDatabase.isOpen())) {
            sWritableDatabase = getInstance(сontext).getWritableDatabase();
        }
        return sWritableDatabase;
    }

    public static void addAttentionToDatabase(Context context, long timestamp, int attention) {
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            ContentValues cv = new ContentValues();
            cv.put(NeurodataDatabase.TIMESTAMP_COLUMN_NAME, timestamp);
            cv.put(NeurodataDatabase.ATTENTION_COLUMN_NAME, attention);
            sqdb.insert(NeurodataDatabase.ATTENTION_TABLE_NAME, null, cv);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }
    }

    public static void addEEGToDatabase(Context context, long timestamp,
                                        int delta, int theta, int lowAlpha, int highAlpha,
                                        int lowBeta, int highBeta, int lowGamma, int midGamma) {
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            ContentValues cv = new ContentValues();
            cv.put(NeurodataDatabase.TIMESTAMP_COLUMN_NAME, timestamp);
            cv.put(NeurodataDatabase.DELTA_COLUMN_NAME, delta);
            cv.put(NeurodataDatabase.THETA_COLUMN_NAME, theta);
            cv.put(NeurodataDatabase.LOW_ALPHA_COLUMN_NAME, lowAlpha);
            cv.put(NeurodataDatabase.HIGH_ALPHA_COLUMN_NAME, highAlpha);
            cv.put(NeurodataDatabase.LOW_BETA_COLUMN_NAME, lowBeta);
            cv.put(NeurodataDatabase.HIGH_BETA_COLUMN_NAME, highBeta);
            cv.put(NeurodataDatabase.LOW_GAMMA_COLUMN_NAME, lowGamma);
            cv.put(NeurodataDatabase.MID_GAMMA_COLUMN_NAME, midGamma);
            sqdb.insert(NeurodataDatabase.EEG_TABLE_NAME, null, cv);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }
    }

    public static void addMediationToDatabase(Context context, long timestamp, int mediation) {
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            ContentValues cv = new ContentValues();
            cv.put(NeurodataDatabase.TIMESTAMP_COLUMN_NAME, timestamp);
            cv.put(NeurodataDatabase.MEDIATION_COLUMN_NAME, mediation);
            sqdb.insert(NeurodataDatabase.MEDIATION_TABLE_NAME, null, cv);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }
    }

    public static List<AttentionMeasurement> getAttentionValues(Context context) {
        Logger.e(TAG, "getAttentionValues");
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        List<AttentionMeasurement> attentionMeasurements = new ArrayList<>();
        try {
            Cursor cursor = sqdb.query(NeurodataDatabase.ATTENTION_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            do {
                AttentionMeasurement measurement = new AttentionMeasurement()
                        .setTimestamp(Helper.processTimestamp(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.TIMESTAMP_COLUMN_NAME))))
                        .setAttentionValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.ATTENTION_COLUMN_NAME)));
                attentionMeasurements.add(measurement);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Logger.e(TAG, "attentionMeasurements.size() " + attentionMeasurements.size());
        return attentionMeasurements;
    }

    public static List<BaseMeasurement> getAttentionBaseValues(Context context) {
        Logger.e(TAG, "getAttentionValues");
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        List<BaseMeasurement> attentionMeasurements = new ArrayList<>();
        try {
            Cursor cursor = sqdb.query(NeurodataDatabase.ATTENTION_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            do {
                AttentionMeasurement measurement = new AttentionMeasurement()
                        .setTimestamp(Helper.processTimestamp(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.TIMESTAMP_COLUMN_NAME))))
                        .setAttentionValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.ATTENTION_COLUMN_NAME)));
                attentionMeasurements.add(measurement);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Logger.e(TAG, "attentionMeasurements.size() " + attentionMeasurements.size());
        return attentionMeasurements;
    }

    public static List<MediationMeasurement> getMediationValues(Context context) {
        Logger.e(TAG, "getMediationValues");
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        List<MediationMeasurement> mediationMeasurements = new ArrayList<>();
        try {
            Cursor cursor = sqdb.query(NeurodataDatabase.MEDIATION_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            do {
                MediationMeasurement measurement = new MediationMeasurement()
                        .setTimestamp(Helper.processTimestamp(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.TIMESTAMP_COLUMN_NAME))))
                        .setMediationValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.MEDIATION_COLUMN_NAME)));
                mediationMeasurements.add(measurement);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Logger.e(TAG, "mediationMeasurements.size() " + mediationMeasurements.size());
        return mediationMeasurements;
    }

    public static List<BaseMeasurement> getMediationBaseValues(Context context) {
        Logger.e(TAG, "getMediationValues");
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        List<BaseMeasurement> mediationMeasurements = new ArrayList<>();
        try {
            Cursor cursor = sqdb.query(NeurodataDatabase.MEDIATION_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            do {
                MediationMeasurement measurement = new MediationMeasurement()
                        .setTimestamp(Helper.processTimestamp(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.TIMESTAMP_COLUMN_NAME))))
                        .setMediationValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.MEDIATION_COLUMN_NAME)));
                mediationMeasurements.add(measurement);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Logger.e(TAG, "mediationMeasurements.size() " + mediationMeasurements.size());
        return mediationMeasurements;
    }

    public static List<EEGMeasurement> getEEGValues(Context context) {
        Logger.e(TAG, "getEEGValues");
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        List<EEGMeasurement> eegMeasurements = new ArrayList<>();
        try {
            Cursor cursor = sqdb.query(NeurodataDatabase.EEG_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            do {
                EEGMeasurement measurement = new EEGMeasurement()
                        .setTimestamp(Helper.processTimestamp(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.TIMESTAMP_COLUMN_NAME))))
                        .setDeltaValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.DELTA_COLUMN_NAME)))
                        .setHighAlphaValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.HIGH_ALPHA_COLUMN_NAME)))
                        .setHighBetaValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.HIGH_BETA_COLUMN_NAME)))
                        .setLowAlphaValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.LOW_ALPHA_COLUMN_NAME)))
                        .setLowBetaValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.LOW_BETA_COLUMN_NAME)))
                        .setLowGammaValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.LOW_GAMMA_COLUMN_NAME)))
                        .setMidGammaValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.MID_GAMMA_COLUMN_NAME)))
                        .setThetaValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.THETA_COLUMN_NAME)));
                eegMeasurements.add(measurement);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Logger.e(TAG, "eegMeasurements.size() " + eegMeasurements.size());
        return eegMeasurements;
    }

    public static List<BaseMeasurement> getEEGBaseValues(Context context) {
        Logger.e(TAG, "getEEGValues");
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        List<BaseMeasurement> eegMeasurements = new ArrayList<>();
        try {
            Cursor cursor = sqdb.query(NeurodataDatabase.EEG_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            do {
                EEGMeasurement measurement = new EEGMeasurement()
                        .setTimestamp(Helper.processTimestamp(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.TIMESTAMP_COLUMN_NAME))))
                        .setDeltaValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.DELTA_COLUMN_NAME)))
                        .setHighAlphaValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.HIGH_ALPHA_COLUMN_NAME)))
                        .setHighBetaValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.HIGH_BETA_COLUMN_NAME)))
                        .setLowAlphaValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.LOW_ALPHA_COLUMN_NAME)))
                        .setLowBetaValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.LOW_BETA_COLUMN_NAME)))
                        .setLowGammaValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.LOW_GAMMA_COLUMN_NAME)))
                        .setMidGammaValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.MID_GAMMA_COLUMN_NAME)))
                        .setThetaValue(cursor.getLong(cursor.getColumnIndex(NeurodataDatabase.THETA_COLUMN_NAME)));
                eegMeasurements.add(measurement);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Logger.e(TAG, "eegMeasurements.size() " + eegMeasurements.size());
        return eegMeasurements;
    }

    public static void clear(Context context) {
        NeurodataDatabase sqh = NeurodataDatabase.getInstance(context);
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            sqdb.delete(MEDIATION_TABLE_NAME, null, null);
            sqdb.delete(EEG_TABLE_NAME, null, null);
            sqdb.delete(ATTENTION_TABLE_NAME, null, null);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        } finally {
            sqh.close();
            sqdb.close();
        }
    }

}
