package com.verbatoria.ui.dashboard.presenter;

/**
 * Презентер для dashboard - main экрана
 *
 * @author nikitaremnev
 */
public interface IDashboardPresenter {

    boolean isBlocked();

    boolean isShowSettings();

    void setShowSettings(boolean showSettings);

}