package com.verbatoria.data.repositories.session.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.provider.BaseColumns;

import com.verbatoria.business.session.processor.ExportProcessor;
import com.verbatoria.data.repositories.session.comparator.EventsComparator;
import com.verbatoria.data.repositories.session.model.BaseMeasurement;
import com.verbatoria.data.repositories.session.model.EventMeasurement;
import com.verbatoria.utils.FileUtils;
import com.verbatoria.utils.Helper;
import com.verbatoria.utils.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * База данных для записи активностей
 *
 * @author nikitaremnev
 */
public class ActivitiesDatabase extends SQLiteOpenHelper implements BaseColumns {

    private static final String TAG = ActivitiesDatabase.class.getSimpleName();

    private static ActivitiesDatabase sDatabaseInstance;
    private static SQLiteDatabase sWritableDatabase;

    private static final String DATABASE_NAME = "activities.db";
    private static final int DATABASE_VERSION = 1;

    private static final String EVENTS_TABLE_NAME = "events";

    private static final String ACTIVITY_CODE_COLUMN_NAME = "activity_code";
    private static final String TIMESTAMP_COLUMN_NAME = "timestamp";

    private static final String EVENT_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + EVENTS_TABLE_NAME + " ("
            + ActivitiesDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TIMESTAMP_COLUMN_NAME + " INTEGER, " +
            ACTIVITY_CODE_COLUMN_NAME + " INTEGER);";

    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE " + EVENTS_TABLE_NAME;

    private static ActivitiesDatabase getInstance(Context context) {
        if (sDatabaseInstance == null) {
            sDatabaseInstance = new ActivitiesDatabase(context);
        }
        return sDatabaseInstance;
    }

    private ActivitiesDatabase(Context context) {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + FileUtils.APPLICATION_FILES_DIRECTORY + File.separator +
                DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(EVENT_SQL_CREATE_ENTRIES);
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

    public static void addEventToDatabase(Context context, String activityCode) {
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            ContentValues cv = new ContentValues();
            cv.put(ActivitiesDatabase.TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());
            cv.put(ActivitiesDatabase.ACTIVITY_CODE_COLUMN_NAME, Long.parseLong(activityCode));
            sqdb.insert(ActivitiesDatabase.EVENTS_TABLE_NAME, null, cv);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }
    }

    public static ArrayList<EventMeasurement> getEvents(Context context) {
        Logger.e(TAG, "getEvents");
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        ArrayList<EventMeasurement> eventMeasurements = new ArrayList<>();
        try {
            Cursor cursor = sqdb.query(ActivitiesDatabase.EVENTS_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            do {
                EventMeasurement eventMeasurement = new EventMeasurement()
                        .setTimestamp(Helper.processTimestamp(cursor.getLong(cursor.getColumnIndex(ActivitiesDatabase.TIMESTAMP_COLUMN_NAME))))
                        .setActivityCode(cursor.getLong(cursor.getColumnIndex(ActivitiesDatabase.ACTIVITY_CODE_COLUMN_NAME)));
                eventMeasurements.add(eventMeasurement);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Collections.sort(eventMeasurements, new EventsComparator());
        Logger.e(TAG, "eventMeasurements.size() " + eventMeasurements.size());
        return eventMeasurements;
    }

    public static void clear(Context context) {
        ActivitiesDatabase sqh = ActivitiesDatabase.getInstance(context);
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            sqdb.delete(EVENTS_TABLE_NAME, null, null);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        } finally {
            sqh.close();
            sqdb.close();
        }
    }

}
