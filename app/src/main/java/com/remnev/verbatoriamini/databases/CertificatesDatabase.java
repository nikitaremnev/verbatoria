package com.remnev.verbatoriamini.databases;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.provider.BaseColumns;

import com.remnev.verbatoriamini.NeuroApplicationClass;
import com.remnev.verbatoriamini.activities.SplashActivity;
import com.remnev.verbatoriamini.adapters.CertificatesAdapter;
import com.remnev.verbatoriamini.model.Certificate;
import com.remnev.verbatoriamini.model.Child;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nikitaremnev on 14.03.16.
 */
public class CertificatesDatabase extends SQLiteOpenHelper implements BaseColumns {


    private static CertificatesDatabase mInstance;
    private static SQLiteDatabase myWritableDb;
    private static final String DATABASE_NAME = "certificates.db";

    private static final int DATABASE_VERSION = 1;

    public static final String CERTIFICATES_TABLE_NAME = "certificates";
    public static final String SPECIALIST_NAME = "name";
    public static final String SPECIALIST_ID = "specialist_id";
    public static final String SPECIALIST_PROFILE = "profile";
    public static final String CREATION_DATE = "creation_date";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String CITY = "city";
    public static final String EXPIRY = "expiry";

    private static final String CERTIFICATES_SQL_CREATE_ENTRIES = "CREATE TABLE "
            + CERTIFICATES_TABLE_NAME + " ("
            + CertificatesDatabase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SPECIALIST_NAME + " TEXT, " +
            SPECIALIST_ID + " TEXT, " +
            SPECIALIST_PROFILE + " INTEGER, " +
            CREATION_DATE + " TEXT, " +
            EMAIL + " TEXT, " +
            PHONE + " TEXT, " +
            EXPIRY + " TEXT, " +
            CITY + " TEXT);";

    private CertificatesDatabase(Context context) {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + SplashActivity.FILES_DIR + File.separator +
                DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CERTIFICATES_SQL_CREATE_ENTRIES);
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

    public static CertificatesDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CertificatesDatabase(context);
        }
        return mInstance;
    }

    public static void addCertificate(Context context, Certificate certificate) {
        SQLiteDatabase sqdb = getMyWritableDatabase(context);
        try {
            ContentValues cv = new ContentValues();
            cv.put(CertificatesDatabase.SPECIALIST_NAME, certificate.getSpecialistName());
            cv.put(CertificatesDatabase.SPECIALIST_ID, certificate.getConstructedID());
            cv.put(CertificatesDatabase.SPECIALIST_PROFILE, certificate.getSpecialistProfile());
            cv.put(CertificatesDatabase.CREATION_DATE, certificate.getCreationDate());
            cv.put(CertificatesDatabase.EMAIL, certificate.getEmail());
            cv.put(CertificatesDatabase.PHONE, certificate.getPhone());
            cv.put(CertificatesDatabase.CITY, certificate.getCity());
            cv.put(CertificatesDatabase.EXPIRY, certificate.getExpiry());
            sqdb.insert(CertificatesDatabase.CERTIFICATES_TABLE_NAME, null, cv);
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }
    }

    public static List<Certificate> readAllCertificates() {
        List<Certificate> certificateList = new ArrayList<>();

        Certificate certificate = new Certificate();
//        certificate.setCity("Москва");
//        certificate.setEmail("sergey@verbatoria.ru");
//        certificate.setSpecialistName("Сергей Раудсепп");
//        certificate.setPhone("89168078498");
//        certificate.setExpiry("16/02/2030");
//        certificateList.add(certificate);
////
        certificate = new Certificate();
        certificate.setCity("Москва");
        certificate.setSpecialistName("Павел Айнутдинов");
        certificate.setEmail("ru1.amelia@verbatoria.ru");
        certificate.setExpiry("16/02/2019");
        certificateList.add(certificate);

        certificate = new Certificate();
        certificate.setCity("Москва");
        certificate.setSpecialistName("Елена Бурданова");
        certificate.setEmail("ru1.amelia@verbatoria.ru");
        certificate.setExpiry("16/02/2019");
        certificateList.add(certificate);

//        certificate = new Certificate();
//        certificate.setCity("Москва");
//        certificate.setSpecialistName("Светлана Миронова");
//        certificate.setExpiry("21/02/2018");
//        certificateList.add(certificate);
//
//        certificate = new Certificate();
//        certificate.setCity("Москва");
//        certificate.setSpecialistName("Айрат Хакимов");
//        certificate.setEmail("ayrat@verbatoria.ru");
//        certificate.setExpiry("22/02/2018");
//        certificateList.add(certificate);
//
//        certificate = new Certificate();
//        certificate.setCity("Москва");
//        certificate.setSpecialistName("Мария Богданович");
//        certificate.setEmail("maria@verbatoria.ru");
//        certificate.setExpiry("22/02/2018");
//        certificateList.add(certificate);
//
//        certificate = new Certificate();
//        certificate.setCity("Москва");
//        certificate.setSpecialistName("Анна Богданович");
//        certificate.setEmail("anna@verbatoria.ru");
//        certificate.setExpiry("30/03/2018");
//        certificateList.add(certificate);
//
//        certificate = new Certificate();
//        certificate.setCity("Москва");
//        certificate.setSpecialistName("Амирханова Альмира");
//        certificate.setEmail("almira@verbatoria.ru");
//        certificate.setExpiry("30/03/2018");
//        certificateList.add(certificate);
//
//        certificate = new Certificate();
//        certificate.setCity("Москва");
//        certificate.setSpecialistName("Степанов Алексей");
//        certificate.setEmail("alexey@verbatoria.ru");
//        certificate.setExpiry("30/03/2018");
//        certificateList.add(certificate);

        return certificateList;


//        SQLiteDatabase sqdb = getMyWritableDatabase(mContext);
//        List<Certificate> certificatesList = new ArrayList<>();
//        Cursor cursor = sqdb.query(CertificatesDatabase.CERTIFICATES_TABLE_NAME, null, null, null, null, null, null);
//        cursor.moveToFirst();
//        do {
//            try {
//                Certificate certificate = new Certificate();
//                certificate.setCity(cursor.getString(cursor.getColumnIndex(CITY)));
//                certificate.setCreationDate(cursor.getString(cursor.getColumnIndex(CREATION_DATE)));
//                certificate.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
//                certificate.setPhone(cursor.getString(cursor.getColumnIndex(PHONE)));
//                certificate.setSpecialistName(cursor.getString(cursor.getColumnIndex(SPECIALIST_NAME)));
//                certificate.setSpecialistID(cursor.getString(cursor.getColumnIndex(SPECIALIST_ID)));
//                certificate.setExpiry(cursor.getString(cursor.getColumnIndex(EXPIRY)));
//                certificate.setSpecialistProfile(cursor.getInt(cursor.getColumnIndex(SPECIALIST_PROFILE)));
//                certificatesList.add(certificate);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        } while (cursor.moveToNext());
//        return certificatesList;
    }

}
