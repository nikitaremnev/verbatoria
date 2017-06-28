package com.verbatoria.presentation.dashboard.presenter;

import android.support.annotation.NonNull;

import com.verbatoria.presentation.dashboard.view.IDashboardView;

/**
 * Презентер для dashboard
 *
 * @author nikitaremnev
 */
public interface IDashboardPresenter {

    void bindView(@NonNull IDashboardView dashboardView);
    void unbindView();

    void readToken();
}
