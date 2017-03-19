package com.remnev.verbatoriamini.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.provider.BaseColumns;

import com.remnev.verbatoriamini.activities.SplashActivity;
import com.remnev.verbatoriamini.model.Child;

import java.io.File;

/**
 * Created by nikitaremnev on 14.03.16.
 */
public class ChildsDatabase extends SQLiteOpenHelper implements BaseColumns {


    private static ChildsDatabase mInstance;
    private static SQLiteDatabase myWritableDb;
    private static final String DATABASE_NAME = "childs.db";

    private static final int DATABASE_VERSION = 1;

    public static final String CHILDS_TABLE_NAME = "childs";
    public static final String CHILD_ID = "child_id";
    public static final String FIO_CHILD = "child_fio";
    public static final String FIO_PARENT = "parent_fio";
    public static final String CREATION_DATE = "date";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String CITY = "city";
    public static final String DIAGNOSE = "diagnose";
    public static final String NEUROPROFILE = "neuroprofile";
    public static final String BIRTHDAY = "birthday";

    private static final String CHILDS_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + CHILDS_TABLE_NAME + " ("
            + ChildsDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CHILD_ID + " TEXT, " +
            FIO_CHILD + " TEXT, " +
            FIO_PARENT + " TEXT, " +
            CREATION_DATE + " TEXT, " +
            EMAIL + " TEXT, " +
            PHONE + " TEXT, " +
            CITY + " TEXT, " +
            DIAGNOSE + " TEXT, " +
            BIRTHDAY + " TEXT, " +
            NEUROPROFILE + " TEXT);";

    private ChildsDatabase(Context context) {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + SplashActivity.FILES_DIR + File.separator +
                DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CHILDS_SQL_CREATE_ENTRIES);
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

    public static ChildsDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ChildsDatabase(context);
        }
        return mInstance;
    }

    public static void addChild(Context context, Child child) {
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            ContentValues cv = new ContentValues();
            cv.put(ChildsDatabase.CHILD_ID, child.getChildID());
            cv.put(ChildsDatabase.FIO_CHILD, child.getChildName());
            cv.put(ChildsDatabase.FIO_PARENT, child.getParentName());
            cv.put(ChildsDatabase.CREATION_DATE, child.getCreationDate());
            cv.put(ChildsDatabase.EMAIL, child.getEmail());
            cv.put(ChildsDatabase.PHONE, child.getPhone());
            cv.put(ChildsDatabase.CITY, child.getCity());
            cv.put(ChildsDatabase.DIAGNOSE, child.getDiagnose());
            cv.put(ChildsDatabase.BIRTHDAY, child.getBirthday());
            cv.put(ChildsDatabase.NEUROPROFILE, child.getNeuroprofile());
            sqdb.insert(ChildsDatabase.CHILDS_TABLE_NAME, null, cv);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }
    }

}
