package com.verbatoria.presentation.dashboard.presenter.settings;

import android.support.annotation.NonNull;

import com.verbatoria.presentation.dashboard.view.settings.ISettingsView;

/**
 * Презентер для dashboard - settings экрана
 *
 * @author nikitaremnev
 */
public interface ISettingsPresenter {

    void bindView(@NonNull ISettingsView settingsView);

    void unbindView();

    void onDeveloperInfoClicked();

    void onQuitClicked();

    void onScheduleClicked();

    void onClearDatabaseClicked();

    void onLateSendClicked();

    void clearDatabase();

    void onLanguageClicked();

    void onRussianLanguageSelected();

    void onEnglishLanguageSelected();

}
