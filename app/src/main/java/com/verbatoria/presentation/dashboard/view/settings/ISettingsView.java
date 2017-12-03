package com.verbatoria.presentation.dashboard.view.settings;

/**
 * Интерфейс вьюхи для отображения настроек
 *
 * @author nikitaremnev
 */
public interface ISettingsView {

    void showDeveloperInfo(String version, String androidVersion);
//    void showConnection();
    void showLogin();
    void showSchedule();

}
