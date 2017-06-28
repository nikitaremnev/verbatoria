package com.verbatoria.di.dashboard;

import com.verbatoria.business.token.interactor.ITokenInteractor;
import com.verbatoria.presentation.dashboard.presenter.DashboardPresenter;
import com.verbatoria.presentation.dashboard.presenter.IDashboardPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Модуль даггера для dashboard
 *
 * @author nikitaremnev
 */
@Module
public class DashboardModule {

    @Provides
    @DashboardScope
    IDashboardPresenter provideDashboardPresenter(ITokenInteractor tokenInteractor) {
        return new DashboardPresenter(tokenInteractor);
    }

}
