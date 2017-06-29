package com.verbatoria.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.verbatoria.VerbatoriaApplication;
import javax.inject.Inject;

/**
 * Реализация SharedPreferences
 *
 * @author nikitaremnev
 */
public class PreferencesStorage {

    private static final String TAG = PreferencesStorage.class.getSimpleName();

    private static final String TOKEN_PREFS = "token";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String EXPIRES_TOKEN = "EXPIRES_TOKEN";

    private SharedPreferences mTokenPreferences;

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
            mTokenPreferences = mContext.getSharedPreferences(TOKEN_PREFS, Context.MODE_PRIVATE);
        }
    }

    public void setAccessToken(String token) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putString(ACCESS_TOKEN, token);
        editor.apply();
    }

    public String getAccessToken() {
        return mTokenPreferences.getString(ACCESS_TOKEN, null);
    }

    public void setExpiresToken(String expiresToken) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putString(EXPIRES_TOKEN, expiresToken);
        editor.apply();
    }

    public String getExpiresToken() {
        return mTokenPreferences.getString(EXPIRES_TOKEN, null);
    }

}
