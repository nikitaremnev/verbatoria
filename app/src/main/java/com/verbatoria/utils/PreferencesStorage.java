package com.verbatoria.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.verbatoria.VerbatoriaApplication;

import java.util.Map;

import javax.inject.Inject;

/**
 * Реализация SharedPreferences
 *
 * @author nikitaremnev
 */
public class PreferencesStorage {

    private static final String TAG = PreferencesStorage.class.getSimpleName();

    private static final String TOKEN_PREFERENCES = "token";
    private static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY";
    private static final String EXPIRES_TOKEN_KEY = "EXPIRES_TOKEN_KEY";

    private static final String QUESTIONS_PREFERENCES = "questions";
    private static final String ANSWERS_KEY = "ANSWERS_KEY";

    private SharedPreferences mTokenPreferences;
    private SharedPreferences mQuestionsPreferences;

    private static PreferencesStorage sInstance = null;

    @Inject
    public Context mContext;

    public static PreferencesStorage getInstance() {
        if (sInstance == null)
            synchronized (PreferencesStorage.class) {
                if (sInstance == null) {
                    sInstance = new PreferencesStorage();
                }
            }
        return sInstance;
    }

    private PreferencesStorage() {
        VerbatoriaApplication.getApplicationComponent().inject(this);
        if (mContext != null) {
            mTokenPreferences = mContext.getSharedPreferences(TOKEN_PREFERENCES, Context.MODE_PRIVATE);
            mQuestionsPreferences = mContext.getSharedPreferences(QUESTIONS_PREFERENCES, Context.MODE_PRIVATE);
        }
    }

    public void setAccessToken(String token) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putString(ACCESS_TOKEN_KEY, token);
        editor.apply();
    }

    public String getAccessToken() {
        return mTokenPreferences.getString(ACCESS_TOKEN_KEY, null);
    }

    public void setExpiresToken(String expiresToken) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putString(EXPIRES_TOKEN_KEY, expiresToken);
        editor.apply();
    }

    public String getExpiresToken() {
        return mTokenPreferences.getString(EXPIRES_TOKEN_KEY, null);
    }

    public int getQuestionAnswer(String index) {
        return mQuestionsPreferences.getInt(index, -1);
    }

    public void setQuestionAnswer(String index, int value) {
        SharedPreferences.Editor editor = mQuestionsPreferences.edit();
        editor.putInt(index, value);
        editor.apply();
    }

    public boolean isAllQuestionsAnswered() {
        Map<String, ?> all = mQuestionsPreferences.getAll();
        return all.containsKey("0") && all.containsKey("1") && all.containsKey("2")
                && all.containsKey("3") && all.containsKey("4")
                && all.containsKey("5") && all.containsKey("6");
    }

    public void clearAnswers() {
        mQuestionsPreferences.edit().clear().apply();
    }

}
