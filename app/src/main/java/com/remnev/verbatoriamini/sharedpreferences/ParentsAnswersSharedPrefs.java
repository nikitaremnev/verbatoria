package com.remnev.verbatoriamini.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.remnev.verbatoriamini.R;

import java.util.Map;

/**
 * Created by nikitaremnev on 21.03.15.
 */
public class ParentsAnswersSharedPrefs {

    private static String TITLE = "parents_answers";

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

    public static int getValue(Context context, String index) {
        return getInstance(context).getInt(index, -1);
    }

    public static void setValue(Context context, String index, int value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(index, value);
        editor.commit();
    }

    public static boolean isAllQuestionsAnswered(Context context) {
        Map<String, ?> all = getInstance(context).getAll();
        return all.containsKey("0") && all.containsKey("1") && all.containsKey("2")
                && all.containsKey("3") && all.containsKey("4")
                && all.containsKey("5") && all.containsKey("6");
    }

    public static void clear(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.clear();
        editor.commit();
    }

}
