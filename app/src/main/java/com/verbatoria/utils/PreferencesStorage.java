package com.verbatoria.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.business.token.models.UserStatus;

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
    private static final String STATUS_TOKEN_KEY = "STATUS_TOKEN_KEY";
    private static final String LAST_LOGIN_KEY = "LAST_LOGIN_KEY";
    private static final String COUNTRY_KEY = "COUNTRY_KEY";

    private static final String QUESTIONS_PREFERENCES = "questions";
    private static final String ANSWERS_KEY = "ANSWERS_KEY";

    private static final String SESSION_PREFERENCES = "questions";
    private static final String LAST_REPORT_NAME_KEY = "LAST_REPORT_NAME_KEY";
    private static final String CURRENT_SESSION_ID = "CURRENT_SESSION_ID";
    private static final String LAST_DATE_KEY = "LAST_DATE_KEY";

    private static final String CACHE_PREFERENCES = "cache";
    private static final String VERBATOLOG_FIRST_NAME_KEY = "VERBATOLOG_FIRST_NAME_KEY";
    private static final String VERBATOLOG_LAST_NAME_KEY = "VERBATOLOG_LAST_NAME_KEY";
    private static final String VERBATOLOG_MIDDLE_NAME_KEY = "VERBATOLOG_MIDDLE_NAME_KEY";
    private static final String VERBATOLOG_PHONE_KEY = "VERBATOLOG_PHONE_KEY";
    private static final String VERBATOLOG_EMAIL_KEY = "VERBATOLOG_EMAIL_KEY";
    private static final String VERBATOLOG_LOCATION_ID_KEY = "VERBATOLOG_LOCATION_ID_KEY";

    private SharedPreferences mTokenPreferences;
    private SharedPreferences mQuestionsPreferences;
    private SharedPreferences mCachePreferences;

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
            mCachePreferences = mContext.getSharedPreferences(CACHE_PREFERENCES, Context.MODE_PRIVATE);
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

    public void setCountry(String country) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putString(COUNTRY_KEY, country);
        editor.apply();
    }

    public String getCountry() {
        return mTokenPreferences.getString(COUNTRY_KEY, mContext.getString(R.string.country_russia));
    }

    public void setExpiresToken(String expiresToken) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putString(EXPIRES_TOKEN_KEY, expiresToken);
        editor.apply();
    }

    public String getExpiresToken() {
        return mTokenPreferences.getString(EXPIRES_TOKEN_KEY, null);
    }

    public void setLastLogin(String login) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putString(LAST_LOGIN_KEY, login);
        editor.apply();
    }

    public String getLastLogin() {
        return mTokenPreferences.getString(LAST_LOGIN_KEY, "");
    }

    public void setUserStatus(String status) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putString(STATUS_TOKEN_KEY, status);
        editor.apply();
    }

    public String getUserStatus() {
        return mTokenPreferences.getString(STATUS_TOKEN_KEY, UserStatus.ACTIVE_STATUS);
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

    public void setLastReportName(String fileName) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putString(LAST_REPORT_NAME_KEY, fileName);
        editor.apply();
    }

    public String getLastReportName() {
        return mTokenPreferences.getString(LAST_REPORT_NAME_KEY, null);
    }

    public void setVerbatologInfo(VerbatologModel verbatologModel) {
        SharedPreferences.Editor editor = mCachePreferences.edit();
        editor.putString(VERBATOLOG_FIRST_NAME_KEY, verbatologModel.getFirstName());
        editor.putString(VERBATOLOG_LAST_NAME_KEY, verbatologModel.getLastName());
        editor.putString(VERBATOLOG_MIDDLE_NAME_KEY, verbatologModel.getMiddleName());
        editor.putString(VERBATOLOG_PHONE_KEY, verbatologModel.getPhone());
        editor.putString(VERBATOLOG_LOCATION_ID_KEY, verbatologModel.getLocationId());
        editor.putString(VERBATOLOG_EMAIL_KEY, verbatologModel.getEmail());
        editor.apply();
    }

    public VerbatologModel getVerbatologInfo() {
        return new VerbatologModel()
                .setLocationId(mCachePreferences.getString(VERBATOLOG_LOCATION_ID_KEY, null))
                .setEmail(mCachePreferences.getString(VERBATOLOG_EMAIL_KEY, null))
                .setFirstName(mCachePreferences.getString(VERBATOLOG_FIRST_NAME_KEY, null))
                .setLastName(mCachePreferences.getString(VERBATOLOG_LAST_NAME_KEY, null))
                .setMiddleName(mCachePreferences.getString(VERBATOLOG_MIDDLE_NAME_KEY, null))
                .setPhone(mCachePreferences.getString(VERBATOLOG_PHONE_KEY, null));

    }

    public String getLocationId() {
        return mCachePreferences.getString(VERBATOLOG_LOCATION_ID_KEY, null);
    }

    public void setCurrentSessionId(String sessionId) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putString(CURRENT_SESSION_ID, sessionId);
        editor.apply();
    }

    public String getCurrentSessionId() {
        return mTokenPreferences.getString(CURRENT_SESSION_ID, null);
    }

    public void setLastDate(String lastDate) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putString(LAST_DATE_KEY, lastDate);
        editor.apply();
    }

    public String getLastDate() {
        return mTokenPreferences.getString(LAST_DATE_KEY, null);
    }
}
