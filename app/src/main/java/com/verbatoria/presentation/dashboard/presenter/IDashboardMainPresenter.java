package com.verbatoria.presentation.dashboard.presenter;

import android.support.annotation.NonNull;

import com.verbatoria.presentation.dashboard.view.main.IDashboardMainView;

/**
 * Презентер для dashboard
 *
 * @author nikitaremnev
 */
public interface IDashboardMainPresenter {

    void bindView(@NonNull IDashboardMainView dashboardView);
    void unbindView();

    void updateVerbatologInfo();
    void updateVerbatologEvents();
}
