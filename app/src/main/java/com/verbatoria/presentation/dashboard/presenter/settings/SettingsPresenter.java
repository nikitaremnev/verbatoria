package com.verbatoria.presentation.dashboard.presenter.settings;

import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.business.dashboard.models.LocationModel;
import com.verbatoria.presentation.dashboard.view.settings.ISettingsView;

import java.util.List;

/**
 * Реализация презентера для settings
 *
 * @author nikitaremnev
 */

public class SettingsPresenter implements ISettingsPresenter {

    private static final String TAG = SettingsPresenter.class.getSimpleName();

    private static final String RUSSIAN_LOCALE = "ru";
    private static final String ENGLISH_LOCALE = "en";
    private static final String HONG_KONG_LOCALE = "zh-CN";

    private IDashboardInteractor mDashboardInteractor;
    private ISettingsView mSettingsView;

    private LocationModel mCurrentModel;

    public SettingsPresenter(IDashboardInteractor dashboardInteractor) {
        mDashboardInteractor = dashboardInteractor;
    }

    @Override
    public void bindView(@NonNull ISettingsView settingsView) {
        mSettingsView = settingsView;
    }

    @Override
    public void unbindView() {
        mSettingsView = null;
    }

    @Override
    public void onDeveloperInfoClicked() {
        mSettingsView.showDeveloperInfo(
                mDashboardInteractor.getApplicationVersion(),
                mDashboardInteractor.getAndroidVersion()
        );
    }

    @Override
    public void onQuitClicked() {
        mSettingsView.showLogin();
    }

    @Override
    public void onScheduleClicked() {
        mDashboardInteractor.setShowSettings(true);
        mSettingsView.showSchedule();
    }

    @Override
    public void onClearDatabaseClicked() {
        mSettingsView.showClearDatabaseConfirmation();
    }

    @Override
    public void onLateSendClicked() {
        mDashboardInteractor.setShowSettings(true);
        mSettingsView.showLateSend();
    }

    @Override
    public void clearDatabase() {
        mDashboardInteractor.cleanUpDatabase()
                .subscribe(this::handleDatabaseCleared);
    }

    @Override
    public void onLanguageClicked() {
        mCurrentModel = mDashboardInteractor.getCurrentLocation();
        List<String> availableLanguages = mCurrentModel.getAvailableLocales();
        if (availableLanguages != null && !availableLanguages.isEmpty()) {
            mSettingsView.showLanguagesDialog(availableLanguages.contains(RUSSIAN_LOCALE), availableLanguages.contains(ENGLISH_LOCALE), availableLanguages.contains(HONG_KONG_LOCALE));
        } else {
            mSettingsView.showLanguagesDialog(true, false, false);
        }
    }

    @Override
    public void onRussianLanguageSelected() {
        mSettingsView.startProgress();
        mDashboardInteractor.updateCurrentLocale(mCurrentModel.getId(), RUSSIAN_LOCALE)
                .doOnUnsubscribe(() -> mSettingsView.stopProgress())
                .subscribe(this::handleRussianLanguageUpdated, this::handleLanguageUpdateError);
    }

    @Override
    public void onEnglishLanguageSelected() {
        mSettingsView.startProgress();
        mDashboardInteractor.updateCurrentLocale(mCurrentModel.getId(), ENGLISH_LOCALE)
                .doOnUnsubscribe(() -> mSettingsView.stopProgress())
                .subscribe(this::handleEnglishLanguageUpdated, this::handleLanguageUpdateError);
    }

    @Override
    public void onHongKongLanguageSelected() {
        mSettingsView.startProgress();
        mDashboardInteractor.updateCurrentLocale(mCurrentModel.getId(), HONG_KONG_LOCALE)
                .doOnUnsubscribe(() -> mSettingsView.stopProgress())
                .subscribe(this::handleHongKongLanguageUpdated, this::handleLanguageUpdateError);
    }

    private void handleDatabaseCleared() {
        mSettingsView.showDatabaseCleared();
    }

    private void handleRussianLanguageUpdated() {
        mDashboardInteractor.setShowSettings(true);
        mSettingsView.setLanguage(RUSSIAN_LOCALE);
    }

    private void handleEnglishLanguageUpdated() {
        mDashboardInteractor.setShowSettings(true);
        mSettingsView.setLanguage(ENGLISH_LOCALE);
    }

    private void handleHongKongLanguageUpdated() {
        mDashboardInteractor.setShowSettings(true);
        mSettingsView.setLanguage(HONG_KONG_LOCALE);
    }

    private void handleLanguageUpdateError(Throwable throwable) {
        mDashboardInteractor.setShowSettings(true);
        mSettingsView.setLanguage(ENGLISH_LOCALE);
    }

}
