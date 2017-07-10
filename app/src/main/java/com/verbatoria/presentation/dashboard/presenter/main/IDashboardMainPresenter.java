package com.verbatoria.presentation.dashboard.presenter.main;

import android.support.annotation.NonNull;

import com.verbatoria.presentation.dashboard.view.main.IDashboardMainView;

/**
 * Презентер для dashboard - main экрана
 *
 * @author nikitaremnev
 */
public interface IDashboardMainPresenter {

    void bindView(@NonNull IDashboardMainView dashboardView);
    void unbindView();

    void updateVerbatologInfo();
    void updateVerbatologEvents();
}
