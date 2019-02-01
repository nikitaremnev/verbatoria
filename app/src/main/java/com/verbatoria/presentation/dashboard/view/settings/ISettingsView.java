package com.verbatoria.presentation.dashboard.view.settings;

/**
 * Интерфейс вьюхи для отображения настроек
 *
 * @author nikitaremnev
 */

public interface ISettingsView {

    void showDeveloperInfo(String version, String androidVersion);
    void showLogin();
    void showSchedule();
    void showLateSend();

    void showClearDatabaseConfirmation();
    void showDatabaseCleared();

    void showLanguagesDialog(boolean isRussianAvailable, boolean isEnglishAvailable, boolean isHongKongAvailable);

    void setLanguage(String language);

    void startProgress();
    void stopProgress();

    void showUpdateLanguageError();

}
