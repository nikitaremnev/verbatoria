package com.remnev.verbatoriamini.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.bluelinelabs.logansquare.LoganSquare;
import com.remnev.verbatoriamini.BuildConfig;
import com.remnev.verbatoriamini.model.Certificate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikitaremnev on 21.03.15.
 */
public class SpecialistSharedPrefs {

    private static String TITLE = "specialist";
    private static String CURRENT_SPECIALIST = "current_specialist";
    private static String CERTIFICATE_EXPIRED = "expired";
    private static String LAST_CERTIFICATE_CHECK = "last_check";

    private static SharedPreferences mSettings;

    private static SharedPreferences getInstance(Context context) {
        if (mSettings == null) {
            mSettings = context.getSharedPreferences(TITLE, Context.MODE_PRIVATE);
        }
        return mSettings;
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getInstance(context).edit();
    }

    public static Certificate getCurrentSpecialist(Context context) {
        if (BuildConfig.FLAVOR.contains("no_nfc")) {
            Certificate certificate = new Certificate();

//            certificate.setCity("Москва");
//            certificate.setEmail("sergey@verbatoria.ru");
//            certificate.setSpecialistName("Сергей Раудсепп");
//            certificate.setPhone("89168078498");
//            certificate.setExpiry("16/02/2030");

//            certificate.setCity("Москва");
//            certificate.setSpecialistName("Павел Айнутдинов");
//            certificate.setEmail("ru1.amelia@verbatoria.ru");
//            certificate.setExpiry("16/02/2019");

//            certificate.setCity("Москва");
//            certificate.setSpecialistName("Елена Бурданова");
//            certificate.setEmail("ru1.amelia@verbatoria.ru");
//            certificate.setExpiry("16/02/2019");
//////
//            certificate.setCity("Москва");
//            certificate.setSpecialistName("Светлана Миронова");
//            certificate.setExpiry("21/02/2018");
////
//            certificate.setCity("Москва");
//            certificate.setSpecialistName("Айрат Хакимов");
//            certificate.setEmail("ayrat@verbatoria.ru");
//            certificate.setExpiry("22/02/2018");

//            certificate.setCity("Москва");
//            certificate.setSpecialistName("Мария Богданович");
//            certificate.setEmail("maria@verbatoria.ru");
//            certificate.setExpiry("22/02/2018");


//            certificate.setCity("Москва");
//            certificate.setSpecialistName("Анна Богданович");
//            certificate.setEmail("anna@verbatoria.ru");
//            certificate.setExpiry("30/03/2018");
//
//
//            certificate.setCity("Москва");
//            certificate.setSpecialistName("Амирханова Альмира");
//            certificate.setEmail("almira@verbatoria.ru");
//            certificate.setExpiry("30/03/2018");

            certificate.setCity("Москва");
            certificate.setSpecialistName("Степанов Алексей");
            certificate.setEmail("alexey@verbatoria.ru");
            certificate.setExpiry("30/03/2018");
//            Богданович Анна Григорьевна
//            Амирханова Альмира Амангельдиновна
//            Степанов Алексей Викторович
//
//            С 1/04/2017 по 30/03/2018

            return certificate;
        } else {
            try {
                return LoganSquare.parse(decodeString(getInstance(context).getString(CURRENT_SPECIALIST, "")), Certificate.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Certificate();
        }
    }

    public static void setCurrentSpecialist(Context context, Certificate certificate) {
        SharedPreferences.Editor editor = getEditor(context);
        try {
            Log.e("specialist", "specialist: " + LoganSquare.serialize(certificate));

            editor.putString(CURRENT_SPECIALIST, encodeString(LoganSquare.serialize(certificate)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public static boolean getCertificateExpired(Context context) {
        return getInstance(context).getBoolean(CERTIFICATE_EXPIRED, false);
    }

    public static void setCertificateExpired(Context context, boolean flag) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(CERTIFICATE_EXPIRED, flag);
        editor.commit();
    }

    public static long getLastCertificateCheckDate(Context context) {
        if (BuildConfig.FLAVOR.contains("no_nfc")) {
            return System.currentTimeMillis();
        } else {
            return getInstance(context).getLong(LAST_CERTIFICATE_CHECK, 0);
        }
    }

    public static void setLastCertificateCheckDate(Context context, long currentTimeMillis) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putLong(LAST_CERTIFICATE_CHECK, currentTimeMillis);
        editor.commit();
    }

    public static String encodeString(String text) {
        byte[] data = text.getBytes();
//        String string = "";
//        for (int i = 0; i < data.length; i ++) {
//            string += data[i]+ "|";
//        }
//        Log.e("test", "encodeString bytes data: " + string);
        byte[] newData = new byte[data.length];
        for (int i = 0; i < data.length; i ++) {
            newData[i] = (byte) (data[i] + 1);
        }
//        string = "";
//        for (int i = 0; i < newData.length; i ++) {
//            string += newData[i]+ "|";
//        }
//        Log.e("test", "encodeString bytes newData: " + string);
        return new String(newData);
    }

    public static String decodeString(String text) {

        byte[] data = text.getBytes();
//        String string = "";
//        for (int i = 0; i < data.length; i ++) {
//            string += data[i] + "|";
//        }
//        Log.e("test", "decodeString bytes data: " + string);
        byte[] newData = new byte[data.length];
        for (int i = 0; i < data.length; i ++) {
            newData[i] = (byte) (data[i] - 1);
        }
//        string = "";
//        for (int i = 0; i < newData.length; i ++) {
//            string += newData[i]+ "|";
//        }
//        Log.e("test", "decodeString bytes newData: " + string);
        String newDataString = new String(newData);
        char[] newDataStringArray = newDataString.toCharArray();
//        Log.e("newDataString", "decodeString: " + newDataString);
        char[] possibleSymbols = "0123456789{}\\/\"[] :QWERTYUIOPASDFGHJKLZXCVBNMЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЁЯЧСМИТЬБЮ?qwertyuiopasdfghjklzxcvbnm,.-+=!@#$%^&*();'|><йцукенгшщзхъфывапролджэячсмитьбюё".toCharArray();
        for (int i = 0; i < newDataStringArray.length; i ++) {
            boolean found = false;
            char currentChar = newDataStringArray[i];
            for (int j = 0; j < possibleSymbols.length; j ++) {
                if (currentChar == possibleSymbols[j]) {
                    found = true;
                    break;
                }
            }
//            Log.e("newDataString", "currentChar: " + currentChar + ", found: " + found);
            if (!found) {
                newDataStringArray[i] = 'п';
                char[] newArray = new char[newDataStringArray.length - 1];
                for (int j = 0; j < i + 1; j ++) {
                    newArray[j] = newDataStringArray[j];
                }
                for (int j = i + 2; j < newDataStringArray.length; j ++) {
                    newArray[j - 1] = newDataStringArray[j];
                }
                newDataStringArray = newArray;
            }
        }
        return new String(newDataStringArray);
    }

}
