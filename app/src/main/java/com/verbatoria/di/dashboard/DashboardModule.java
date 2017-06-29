package com.verbatoria.di.dashboard;

import com.verbatoria.business.dashboard.DashboardInteractor;
import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.business.token.interactor.ITokenInteractor;
import com.verbatoria.data.repositories.dashboard.DashboardRepository;
import com.verbatoria.data.repositories.dashboard.IDashboardRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.presentation.dashboard.presenter.DashboardPresenter;
import com.verbatoria.presentation.dashboard.presenter.IDashboardPresenter;
import com.verbatoria.utils.rx.IRxSchedulers;

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
    IDashboardRepository provideDashboardRepository() {
        return new DashboardRepository();
    }

    @Provides
    @DashboardScope
    IDashboardInteractor provideDashboardInteractor(IDashboardRepository dashboardRepository,
                                                    ITokenRepository tokenRepository) {
        return new DashboardInteractor(dashboardRepository, tokenRepository);
    }

    @Provides
    @DashboardScope
    IDashboardPresenter provideDashboardPresenter(IDashboardInteractor dashboardInteractor,
                                                  IRxSchedulers rxSchedulers) {
        return new DashboardPresenter(dashboardInteractor, rxSchedulers);
    }


}
