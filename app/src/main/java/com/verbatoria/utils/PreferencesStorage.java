package com.verbatoria.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remnev.verbatoria.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.AgeGroupModel;
import com.verbatoria.business.dashboard.models.LocationModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.business.token.models.UserStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static com.verbatoria.utils.LocaleHelper.LOCALE_RU;

/**
 * Реализация SharedPreferences
 *
 * @author nikitaremnev
 */
public class PreferencesStorage {

    private static final String TOKEN_PREFERENCES = "token";
    private static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY";
    private static final String EXPIRES_TOKEN_KEY = "EXPIRES_TOKEN_KEY";
    private static final String STATUS_TOKEN_KEY = "STATUS_TOKEN_KEY";
    private static final String LAST_SMS_CONFIRMATION_KEY = "LAST_SMS_CONFIRMATION_KEY";
    private static final String LAST_SMS_CODE_KEY = "LAST_SMS_CODE_KEY";
    private static final String LAST_LOGIN_KEY = "LAST_LOGIN_KEY";
    private static final String COUNTRY_KEY = "COUNTRY_KEY";
    private static final String CURRENT_AGE_KEY = "CURRENT_AGE_KEY";
    private static final String SHOW_SETTINGS_KEY = "SHOW_SETTINGS_KEY";
    private static final String IS_ARCHIMED_ALLOWED_FOR_VERBATOLOG_KEY = "IS_ARCHIMED_ALLOWED_FOR_VERBATOLOG_KEY";
    private static final String IS_SCHOOL_ACCOUNT_KEY = "IS_SCHOOL_ACCOUNT_KEY";

    private static final String QUESTIONS_PREFERENCES = "questions";

    private static final String LAST_REPORT_NAME_KEY = "LAST_REPORT_NAME_KEY";
    private static final String CURRENT_SESSION_ID_KEY = "CURRENT_SESSION_ID_KEY";
    private static final String LAST_DATE_KEY = "LAST_DATE_KEY";
    private static final String CURRENT_LOCALE_KEY = "CURRENT_LOCALE_KEY";

    private static final String CACHE_PREFERENCES = "cache";
    private static final String VERBATOLOG_FIRST_NAME_KEY = "VERBATOLOG_FIRST_NAME_KEY";
    private static final String VERBATOLOG_LAST_NAME_KEY = "VERBATOLOG_LAST_NAME_KEY";
    private static final String VERBATOLOG_MIDDLE_NAME_KEY = "VERBATOLOG_MIDDLE_NAME_KEY";
    private static final String VERBATOLOG_PHONE_KEY = "VERBATOLOG_PHONE_KEY";
    private static final String VERBATOLOG_EMAIL_KEY = "VERBATOLOG_EMAIL_KEY";
    private static final String VERBATOLOG_LOCATION_ID_KEY = "VERBATOLOG_LOCATION_ID_KEY";
    private static final String VERBATOLOG_IS_ARCHIMED_ALLOWED_KEY = "VERBATOLOG_IS_ARCHIMED_ALLOWED_KEY";

    private static final String LOCATION_ADDRESS_KEY = "LOCATION_ADDRESS_KEY";
    private static final String LOCATION_CITY_KEY = "LOCATION_CITY_KEY";
    private static final String LOCATION_COUNTRY_KEY = "LOCATION_COUNTRY_KEY";
    private static final String LOCATION_PARTNER_KEY = "LOCATION_PARTNER_KEY";
    private static final String LOCATION_NAME_KEY = "LOCATION_NAME_KEY";
    private static final String LOCATION_LOCALE_KEY = "LOCATION_LOCALE_KEY";
    private static final String LOCATION_AVAILABLE_LOCALES_KEY = "LOCATION_AVAILABLE_LOCALES_KEY";
    private static final String LOCATION_ID_KEY = "LOCATION_ID_KEY";

    private static final String AGE_GROUPS_KEY = "AGE_GROUPS_KEY";

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
        VerbatoriaApplication.getInjector().inject(this);
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

    public void setCurrentAge(int age) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putInt(CURRENT_AGE_KEY, age);
        editor.apply();
    }

    public int getCurrentAge() {
        return mTokenPreferences.getInt(CURRENT_AGE_KEY, 0);
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

    public void setLastSMSCode(Long smscode) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putLong(LAST_SMS_CODE_KEY, smscode);
        editor.apply();
    }

    public Long getLastSMSCode() {
        return mTokenPreferences.getLong(LAST_SMS_CODE_KEY, 0L);
    }

    public void setLastSmsConfirmation(Long time) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putLong(LAST_SMS_CONFIRMATION_KEY, time);
        editor.apply();
    }

    public Long getLastSmsConfirmation() {
        return mTokenPreferences.getLong(LAST_SMS_CONFIRMATION_KEY, 0L);
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
        editor.putBoolean(VERBATOLOG_IS_ARCHIMED_ALLOWED_KEY, verbatologModel.isArchimedAllowed());
        editor.apply();
    }

    public void setLocationInfo(LocationModel locationModel) {
        SharedPreferences.Editor editor = mCachePreferences.edit();
        editor.putString(LOCATION_ADDRESS_KEY, locationModel.getAddress());
        editor.putString(LOCATION_CITY_KEY, locationModel.getCity());
        editor.putString(LOCATION_COUNTRY_KEY, locationModel.getCountry());
        editor.putString(LOCATION_PARTNER_KEY, locationModel.getPartner());
        editor.putString(LOCATION_NAME_KEY, locationModel.getName());
        editor.putString(LOCATION_ID_KEY, locationModel.getId());
        editor.putString(LOCATION_LOCALE_KEY, locationModel.getLocale());
        editor.putStringSet(LOCATION_AVAILABLE_LOCALES_KEY, new HashSet<>(locationModel.getAvailableLocales()));
        editor.apply();
    }

    public VerbatologModel getVerbatologInfo() {
        return new VerbatologModel()
                .setLocationId(mCachePreferences.getString(VERBATOLOG_LOCATION_ID_KEY, null))
                .setEmail(mCachePreferences.getString(VERBATOLOG_EMAIL_KEY, null))
                .setFirstName(mCachePreferences.getString(VERBATOLOG_FIRST_NAME_KEY, null))
                .setLastName(mCachePreferences.getString(VERBATOLOG_LAST_NAME_KEY, null))
                .setMiddleName(mCachePreferences.getString(VERBATOLOG_MIDDLE_NAME_KEY, null))
                .setPhone(mCachePreferences.getString(VERBATOLOG_PHONE_KEY, null))
                .setIsArchimedAllowed(mCachePreferences.getBoolean(VERBATOLOG_IS_ARCHIMED_ALLOWED_KEY, false));
    }

    public LocationModel getLocationInfo() {
        return new LocationModel()
                .setAddress(mCachePreferences.getString(LOCATION_ADDRESS_KEY, null))
                .setCity(mCachePreferences.getString(LOCATION_CITY_KEY, null))
                .setCountry(mCachePreferences.getString(LOCATION_COUNTRY_KEY, null))
                .setPartner(mCachePreferences.getString(LOCATION_PARTNER_KEY, null))
                .setName(mCachePreferences.getString(LOCATION_NAME_KEY, null))
                .setId(mCachePreferences.getString(LOCATION_ID_KEY, null))
                .setLocale(mCachePreferences.getString(LOCATION_LOCALE_KEY, null))
                .setAvailableLocales(new ArrayList<>(mCachePreferences.getStringSet(LOCATION_AVAILABLE_LOCALES_KEY, Collections.emptySet())));
    }

    public void setLocationId(String locationId) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putString(VERBATOLOG_LOCATION_ID_KEY, locationId);
        editor.apply();
    }

    public String getLocationId() {
        return mCachePreferences.getString(VERBATOLOG_LOCATION_ID_KEY, null);
    }

    public void setCurrentSessionId(String sessionId) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putString(CURRENT_SESSION_ID_KEY, sessionId);
        editor.apply();
    }

    public String getCurrentSessionId() {
        return mTokenPreferences.getString(CURRENT_SESSION_ID_KEY, null);
    }

    public void setLastDate(String lastDate) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putString(LAST_DATE_KEY, lastDate);
        editor.apply();
    }

    public String getLastDate() {
        return mTokenPreferences.getString(LAST_DATE_KEY, null);
    }

    public void setCurrentLocale(String currentLocale) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putString(CURRENT_LOCALE_KEY, currentLocale);
        editor.apply();
    }

    public String getCurrentLocale() {
        return mTokenPreferences.getString(CURRENT_LOCALE_KEY, LOCALE_RU);
    }

    public void setAgeGroups(List<AgeGroupModel> ageGroupModels) {
        ObjectMapper mapper = new ObjectMapper();
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        try {
            editor.putString(AGE_GROUPS_KEY, mapper.writeValueAsString(ageGroupModels));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        editor.apply();
    }

    public List<AgeGroupModel> getAgeGroups() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            TypeReference<List<AgeGroupModel>> mapType = new TypeReference<List<AgeGroupModel>>() {};
            return mapper.readValue(mTokenPreferences.getString(AGE_GROUPS_KEY, null), mapType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setShowSettings(boolean showSettings) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putBoolean(SHOW_SETTINGS_KEY, showSettings);
        editor.apply();
    }

    public boolean getShowSettings() {
        return mTokenPreferences.getBoolean(SHOW_SETTINGS_KEY, false);
    }

    public void setIsArchimedAllowedForVerbatolog(boolean isArchimedAllowedForVerbatolog) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putBoolean(IS_ARCHIMED_ALLOWED_FOR_VERBATOLOG_KEY, isArchimedAllowedForVerbatolog);
        editor.apply();
    }

    public boolean isArchimedAllowedForVerbatolog() {
        return mTokenPreferences.getBoolean(IS_ARCHIMED_ALLOWED_FOR_VERBATOLOG_KEY, false);
    }

    public void setIsSchoolAccount(boolean isSchoolAccount) {
        SharedPreferences.Editor editor = mTokenPreferences.edit();
        editor.putBoolean(IS_SCHOOL_ACCOUNT_KEY, isSchoolAccount);
        editor.apply();
    }

    public boolean isSchoolAccount() {
        return mTokenPreferences.getBoolean(IS_SCHOOL_ACCOUNT_KEY, false);
    }
}
