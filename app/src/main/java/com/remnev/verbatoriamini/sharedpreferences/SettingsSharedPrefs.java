package com.remnev.verbatoriamini.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.remnev.verbatoriamini.R;

/**
 * Created by nikitaremnev on 21.03.15.
 */
public class SettingsSharedPrefs {

    private static String TITLE = "settings";
    private static String TIMER = "timer";
    private static String EMAIL = "email";
    private static String GAME = "game_time";
    private static String LANGUAGE = "language";
    private static String FIRST_TIME = "first_time";
    private static String BCI_ID = "bci_id";
    private static String LAST_WRITTEN_WORD = "written_word";


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

    public static int getTimerValue(Context context) {
        return getInstance(context).getInt(TIMER, 10);
    }

    public static void setTimerValue(Context context, int value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(TIMER, value);
        editor.commit();
    }

    public static int getGameTime(Context context) {
        return getInstance(context).getInt(GAME, 30);
    }

    public static void setGameTime(Context context, int value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(GAME, value);
        editor.commit();
    }

    public static String getLanguage(Context context) {
        return getInstance(context).getString(LANGUAGE, "");
    }

    public static void setLanguage(Context context, String language) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(LANGUAGE, language);
        editor.commit();
    }

    public static String getBciID(Context context) {
        return getInstance(context).getString(BCI_ID, "");
    }

    public static void setBciID(Context context, String language) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(BCI_ID, language);
        editor.commit();
    }

    public static String getLastWrittenWord(Context context) {
        return getInstance(context).getString(LAST_WRITTEN_WORD, "");
    }

    public static void setLastWrittenWord(Context context, String word) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(LAST_WRITTEN_WORD, word);
        editor.commit();
    }

    public static String getEmailToSend(Context context) {
        return getInstance(context).getString(EMAIL, context.getString(R.string.email_to_send));
    }

    public static void setEmailToSend(Context context, String email) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public static boolean getIsFirstTime(Context context) {
        return getInstance(context).getBoolean(FIRST_TIME, false);
    }

    public static void setFirstTime(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(FIRST_TIME, true);
        editor.commit();
    }
}
