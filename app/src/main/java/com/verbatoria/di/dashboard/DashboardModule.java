package com.verbatoria.di.dashboard;

import com.verbatoria.business.dashboard.DashboardInteractor;
import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.data.repositories.dashboard.DashboardRepository;
import com.verbatoria.data.repositories.dashboard.IDashboardRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.presentation.dashboard.presenter.calendar.CalendarPresenter;
import com.verbatoria.presentation.dashboard.presenter.calendar.ICalendarPresenter;
import com.verbatoria.presentation.dashboard.presenter.calendar.add.AddCalendarEventPresenter;
import com.verbatoria.presentation.dashboard.presenter.calendar.add.IAddCalendarEventPresenter;
import com.verbatoria.presentation.dashboard.presenter.calendar.detail.CalendarEventDetailPresenter;
import com.verbatoria.presentation.dashboard.presenter.calendar.detail.ICalendarEventDetailPresenter;
import com.verbatoria.presentation.dashboard.presenter.main.DashboardMainPresenter;
import com.verbatoria.presentation.dashboard.presenter.main.IDashboardMainPresenter;
import com.verbatoria.presentation.dashboard.presenter.settings.ISettingsPresenter;
import com.verbatoria.presentation.dashboard.presenter.settings.SettingsPresenter;

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

    @Provides
    @DashboardScope
    ICalendarEventDetailPresenter provideCalendarEventDetailPresenter(IDashboardInteractor dashboardInteractor) {
        return new CalendarEventDetailPresenter(dashboardInteractor);
    }

    @Provides
    @DashboardScope
    ISettingsPresenter provideSettingsPresenter(IDashboardInteractor dashboardInteractor) {
        return new SettingsPresenter(dashboardInteractor);
    }

    @Provides
    @DashboardScope
    ICalendarPresenter provideCalendarPresenter(IDashboardInteractor dashboardInteractor) {
        return new CalendarPresenter(dashboardInteractor);
    }
}
