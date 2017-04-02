package com.remnev.verbatoriamini.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.provider.BaseColumns;

import com.remnev.verbatoriamini.activities.SplashActivity;

import java.io.File;

public class StatisticsDatabase extends SQLiteOpenHelper implements BaseColumns {

    private static StatisticsDatabase mInstance;
    private static SQLiteDatabase myWritableDb;
    private static final String DATABASE_NAME = "statistics.db";

    private static final int DATABASE_VERSION = 4;

    public static final String TIMESTAMP = "timestamp";
    public static final String EVENT_MODE = "event_mode";
    public static final String WORD = "word";
    public static final String MODULE = "module";
    public static final String ACTION = "action";

    //Games
    public static final String GAMES_TABLE_NAME = "games";
    public static final String START_TIMESTAMP = "start_timestamp";
    public static final String FINISH_TIMESTAMP = "finish_timestamp";
    private static final String SUCCESS_TIMESTAMP = "success_timestamp";
    private static final String ATTEMPTS = "attempts";
    public static final String GAME_MODE = "mode";
    public static final String GAME_SUBMODE = "submode";
    private static final String REPEATS_NUMBER = "repeats_number";
    public static final String MISTAKE = "mistake";

    //Learn
    public static final String LEARN_TABLE_NAME = "learn";

    //All cards
    public static final String CARDS_TABLE_NAME = "cards";

    //Events
    public static final String EVENTS_TABLE_NAME = "events";

    private static final String LEARN_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + LEARN_TABLE_NAME + " ("
            + StatisticsDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            START_TIMESTAMP + " INTEGER, " +
            FINISH_TIMESTAMP + " INTEGER, " +
            EVENT_MODE + " INTEGER, " +
            WORD + " TEXT);";

    private static final String CARDS_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + CARDS_TABLE_NAME + " ("
            + StatisticsDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TIMESTAMP + " INTEGER, " +
            EVENT_MODE + " INTEGER, " +
            WORD + " TEXT);";

    private static final String GAMES_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + GAMES_TABLE_NAME + " ("
            + StatisticsDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            START_TIMESTAMP + " INTEGER, " +
            FINISH_TIMESTAMP + " INTEGER, " +
            SUCCESS_TIMESTAMP + " INTEGER, " +
            ATTEMPTS + " TEXT, " +
            WORD + " TEXT, " +
            GAME_MODE + " INTEGER, " +
            MODULE + " TEXT, " +
            GAME_SUBMODE + " INTEGER, " +
            REPEATS_NUMBER + " INTEGER);";

    private static final String EVENT_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + EVENTS_TABLE_NAME + " ("
            + StatisticsDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TIMESTAMP + " INTEGER, " +
//            GAME_MODE + " INTEGER, " +
            GAME_SUBMODE + " INTEGER, " +
            ACTION + " INTEGER, " +
            EVENT_MODE + " INTEGER, " +
            MISTAKE + " TEXT, " +
            WORD + " TEXT, " +
            MODULE + " INTEGER);";


    public static StatisticsDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new StatisticsDatabase(context);
        }
        return mInstance;
    }

    /*
    RecStart, RecEnd, WordStart, WordEnd, WordFail, WordSuccess, WordSkip, Connect, Disconnect
     */
    private StatisticsDatabase(Context context) {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + SplashActivity.FILES_DIR + File.separator +
                DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(LEARN_SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(CARDS_SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(GAMES_SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(EVENT_SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        if (i == 2 && i2 == 3) {
            // If you need to add a column
            sqLiteDatabase.execSQL("ALTER TABLE " + GAMES_TABLE_NAME + " ADD COLUMN " + GAME_SUBMODE + " INTEGER DEFAULT -1");
            sqLiteDatabase.execSQL("ALTER TABLE " + LEARN_TABLE_NAME + " ADD COLUMN " + EVENT_MODE + " INTEGER DEFAULT -1");
            sqLiteDatabase.execSQL("ALTER TABLE " + CARDS_TABLE_NAME + " ADD COLUMN " + EVENT_MODE + " INTEGER DEFAULT -1");
            sqLiteDatabase.execSQL("ALTER TABLE " + GAMES_TABLE_NAME + " ADD COLUMN " + MODULE + " INTEGER DEFAULT -1");
            sqLiteDatabase.execSQL(EVENT_SQL_CREATE_ENTRIES);
        }
        if (i2 == 4) {
            sqLiteDatabase.execSQL("ALTER TABLE " + EVENTS_TABLE_NAME + " ADD COLUMN " + MISTAKE + " TEXT");
        }
    }

    public static void removeAll(Context mContext) {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        StatisticsDatabase sqh = StatisticsDatabase.getInstance(mContext);
        SQLiteDatabase sqdb = sqh.getMyWritableDatabase();
        try {
            sqdb.delete(GAMES_TABLE_NAME, null, null);
            sqdb.delete(LEARN_TABLE_NAME, null, null);
            sqdb.delete(CARDS_TABLE_NAME, null, null);
            sqdb.delete(EVENTS_TABLE_NAME, null, null);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        } finally {
            sqh.close();
            sqdb.close();
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

    public SQLiteDatabase getMyWritableDatabase() {
        if ((myWritableDb == null) || (!myWritableDb.isOpen())) {
            myWritableDb = this.getWritableDatabase();
        }
        return myWritableDb;
    }

    public static void addEventToDatabase(Context context, String word, String module,
                                          int actionID, int eventID, int mode, int submode) {
        StatisticsDatabase sqh = StatisticsDatabase.getInstance(context);
        SQLiteDatabase sqdb = sqh.getMyWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(StatisticsDatabase.TIMESTAMP, System.currentTimeMillis());
            cv.put(StatisticsDatabase.WORD, word);
            cv.put(StatisticsDatabase.MODULE, module);
            cv.put(StatisticsDatabase.GAME_SUBMODE, submode);
            cv.put(StatisticsDatabase.ACTION, actionID);
            cv.put(StatisticsDatabase.EVENT_MODE, eventID);
            sqdb.insert(StatisticsDatabase.EVENTS_TABLE_NAME, null, cv);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }
    }

    public static void addEventToDatabase(Context context, String word, String module,
                                          int actionID, int eventID, int mode, int submode, String mistake) {
        StatisticsDatabase sqh = StatisticsDatabase.getInstance(context);
        SQLiteDatabase sqdb = sqh.getMyWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(StatisticsDatabase.TIMESTAMP, System.currentTimeMillis());
            cv.put(StatisticsDatabase.WORD, word);
            cv.put(StatisticsDatabase.MODULE, module);
            cv.put(StatisticsDatabase.MISTAKE, mistake);
            cv.put(StatisticsDatabase.GAME_SUBMODE, submode);
            cv.put(StatisticsDatabase.ACTION, actionID);
            cv.put(StatisticsDatabase.EVENT_MODE, eventID);
            sqdb.insert(StatisticsDatabase.EVENTS_TABLE_NAME, null, cv);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }
    }

}
