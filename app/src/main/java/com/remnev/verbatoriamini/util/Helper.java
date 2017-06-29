package com.remnev.verbatoriamini.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by nikitaremnev on 26.11.15.
 */
public class Helper {

    public static void showSnackBar(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    public static long processTimestamp(long timestamp) {
        long ostatok = timestamp % 1000;
        if (ostatok > 499) {
            return (timestamp / 1000) + 1;
        } else {
            return timestamp / 1000;
        }
    }

    public static String readTag(Tag tag, Context mContext) {
        Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
            return null;
        }

        NdefMessage ndefMessage = ndef.getCachedNdefMessage();
        if (ndefMessage == null) {
            Log.e("nfc", "nfc ndefMessage is null");
            return null;
        }

        NdefRecord[] records = ndefMessage.getRecords();
        if (records == null) {
            Log.e("nfc", "nfc records is null");
            return null;
        }

        try {
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        String word = readText(ndefRecord);
                        //   StatisticsDatabase.addNFCReadToDatabase(mContext, word, eventMode);
                        return word;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("nfc", "nfc exception");
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    public static String readText(NdefRecord record) throws UnsupportedEncodingException {
        byte[] payload = record.getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
        int languageCodeLength = payload[0] & 0063;
        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
    }

    public static void write(String text, Tag tag, Context mContext) throws IOException, FormatException {
        Log.e("text", "string: " + text);
        Ndef ndef = Ndef.get(tag);
        NdefRecord[] records = {createRecord(text)};
        NdefMessage message = new NdefMessage(records);
        ndef.connect();
        ndef.writeNdefMessage(message);
        ndef.close();
    }

    public static NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1 + langLength + textLength];
        payload[0] = (byte) langLength;
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);
        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
        return recordNFC;
    }

}
