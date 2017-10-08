package com.verbatoria.presentation.dashboard.presenter.main;

import android.support.annotation.NonNull;

import com.verbatoria.presentation.dashboard.view.info.IVerbatologInfoView;

/**
 * Презентер для dashboard - main экрана
 *
 * @author nikitaremnev
 */
public interface IVerbatologInfoPresenter {

    void bindView(@NonNull IVerbatologInfoView verbatologInfoView);
    void unbindView();

    void updateVerbatologInfo();
    void updateLocationInfo();

}