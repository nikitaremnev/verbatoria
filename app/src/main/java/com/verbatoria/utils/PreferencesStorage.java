package com.verbatoria.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.di.login.LoginModule;

import javax.inject.Inject;

/**
 * Реализация SharedPreferences
 *
 * @author nikitaremnev
 */
public class PreferencesStorage {

    private static final String TAG = PreferencesStorage.class.getSimpleName();

    public static final String TOKEN_PREFS = "token";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String EXPIRES_TOKEN = "EXPIRES_TOKEN";

    private SharedPreferences mTokenPreferences;

    private static PreferencesStorage sInstance = null;

    @Inject
    public Context mContext;

    public void registerListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        mTokenPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        mTokenPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

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
