package com.verbatoria.di.dashboard;

import com.verbatoria.business.dashboard.DashboardInteractor;
import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.data.repositories.dashboard.DashboardRepository;
import com.verbatoria.data.repositories.dashboard.IDashboardRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.presentation.dashboard.presenter.add.AddCalendarEventPresenter;
import com.verbatoria.presentation.dashboard.presenter.add.IAddCalendarEventPresenter;
import com.verbatoria.presentation.dashboard.presenter.main.DashboardMainPresenter;
import com.verbatoria.presentation.dashboard.presenter.main.IDashboardMainPresenter;

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
    IDashboardMainPresenter provideDashboardPresenter(IDashboardInteractor dashboardInteractor) {
        return new DashboardMainPresenter(dashboardInteractor);
    }

    @Provides
    @DashboardScope
    IAddCalendarEventPresenter provideAddCalendarEventPresenter(IDashboardInteractor dashboardInteractor) {
        return new AddCalendarEventPresenter(dashboardInteractor);
    }

}
