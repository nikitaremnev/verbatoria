package com.verbatoria.data.repositories.session.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.provider.BaseColumns;

import com.verbatoria.business.late_send.models.LateReportModel;
import com.verbatoria.data.repositories.late_send.comparator.LateSendComparator;
import com.verbatoria.utils.DateUtils;
import com.verbatoria.utils.FileUtils;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * База данных для поздней отправки отчетов
 *
 * @author nikitaremnev
 */
public class LateReportsDatabase extends SQLiteOpenHelper implements BaseColumns {

    private static final String TAG = LateReportsDatabase.class.getSimpleName();

    private static LateReportsDatabase sDatabaseInstance;
    private static SQLiteDatabase sWritableDatabase;

    private static final String DATABASE_NAME = "late_reports.db";
    private static final int DATABASE_VERSION = 1;

    private static final String LATE_REPORTS_TABLE_NAME = "late_reports";

    private static final String SESSION_ID_COLUMN_NAME = "session_id";
    private static final String START_AT_COLUMN_NAME = "start_at";
    private static final String END_AT_COLUMN_NAME = "end_at";
    private static final String CHILD_NAME_COLUMN_NAME = "child_name";
    private static final String REPORT_ID_COLUMN_NAME = "report_id";
    private static final String REPORT_FILE_NAME_COLUMN_NAME = "report_file";

    private static final String LATE_REPORTS_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + LATE_REPORTS_TABLE_NAME + " ("
            + LateReportsDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SESSION_ID_COLUMN_NAME + " TEXT, " +
            START_AT_COLUMN_NAME + " TEXT, " +
            END_AT_COLUMN_NAME + " TEXT, " +
            CHILD_NAME_COLUMN_NAME + " TEXT, " +
            REPORT_ID_COLUMN_NAME + " TEXT, " +
            REPORT_FILE_NAME_COLUMN_NAME + " TEXT);";

    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE " + LATE_REPORTS_TABLE_NAME;

    private static LateReportsDatabase getInstance(Context context) {
        if (sDatabaseInstance == null) {
            sDatabaseInstance = new LateReportsDatabase(context);
        }
        return sDatabaseInstance;
    }

    private LateReportsDatabase(Context context) {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + FileUtils.APPLICATION_FILES_DIRECTORY + File.separator +
                DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(LATE_REPORTS_SQL_CREATE_ENTRIES);
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

    public static void addReportBackup(Context context, LateReportModel lateReportModel) {
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            ContentValues cv = new ContentValues();
            cv.put(LateReportsDatabase.SESSION_ID_COLUMN_NAME, lateReportModel.getSessionId());
            cv.put(LateReportsDatabase.START_AT_COLUMN_NAME, DateUtils.toServerDateTimeWithoutConvertingString(lateReportModel.getStartAt().getTime()));
            cv.put(LateReportsDatabase.END_AT_COLUMN_NAME, DateUtils.toServerDateTimeWithoutConvertingString(lateReportModel.getEndAt().getTime()));
            cv.put(LateReportsDatabase.CHILD_NAME_COLUMN_NAME, lateReportModel.getChildName());
            cv.put(LateReportsDatabase.REPORT_ID_COLUMN_NAME, lateReportModel.getReportId());
            cv.put(LateReportsDatabase.REPORT_FILE_NAME_COLUMN_NAME, lateReportModel.getReportFileName());
            sqdb.insert(LateReportsDatabase.LATE_REPORTS_TABLE_NAME, null, cv);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void removeReport(Context context, LateReportModel lateReportModel) {
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            sqdb.delete(LateReportsDatabase.LATE_REPORTS_TABLE_NAME, SESSION_ID_COLUMN_NAME + " = " + lateReportModel.getSessionId(), null);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }
    }

    public static ArrayList<LateReportModel> getLateReportModels(Context context) {
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        ArrayList<LateReportModel> lateReportModels = new ArrayList<>();
        try {
            Cursor cursor = sqdb.query(LateReportsDatabase.LATE_REPORTS_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            do {
                LateReportModel lateReportModel = new LateReportModel()
                        .setReportFileName(cursor.getString(cursor.getColumnIndex(LateReportsDatabase.REPORT_FILE_NAME_COLUMN_NAME)))
                        .setReportId(cursor.getString(cursor.getColumnIndex(LateReportsDatabase.REPORT_ID_COLUMN_NAME)))
                        .setChildName(cursor.getString(cursor.getColumnIndex(LateReportsDatabase.CHILD_NAME_COLUMN_NAME)))
                        .setSessionId(cursor.getString(cursor.getColumnIndex(LateReportsDatabase.SESSION_ID_COLUMN_NAME)))
                        .setStartAt(DateUtils.parseDateTime(cursor.getString(cursor.getColumnIndex(LateReportsDatabase.START_AT_COLUMN_NAME))))
                        .setEndAt(DateUtils.parseDateTime(cursor.getString(cursor.getColumnIndex(LateReportsDatabase.END_AT_COLUMN_NAME))));
                lateReportModels.add(lateReportModel);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Collections.sort(lateReportModels, new LateSendComparator());
        return lateReportModels;
    }

    public static void clear(Context context) {
        LateReportsDatabase sqh = LateReportsDatabase.getInstance(context);
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            sqdb.delete(LATE_REPORTS_TABLE_NAME, null, null);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        } finally {
            sqh.close();
            sqdb.close();
        }
    }

}
