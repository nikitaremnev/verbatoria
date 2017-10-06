package com.verbatoria.di.dashboard;

import com.verbatoria.business.calendar.CalendarInteractor;
import com.verbatoria.business.calendar.ICalendarInteractor;
import com.verbatoria.business.dashboard.DashboardInteractor;
import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.data.repositories.calendar.CalendarRepository;
import com.verbatoria.data.repositories.calendar.ICalendarRepository;
import com.verbatoria.data.repositories.dashboard.DashboardRepository;
import com.verbatoria.data.repositories.dashboard.IDashboardRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.presentation.calendar.presenter.CalendarPresenter;
import com.verbatoria.presentation.calendar.presenter.ICalendarPresenter;
import com.verbatoria.presentation.calendar.presenter.add.AddCalendarEventPresenter;
import com.verbatoria.presentation.calendar.presenter.add.IAddCalendarEventPresenter;
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
    ICalendarRepository provideCalendarRepository() {
        return new CalendarRepository();
    }

    @Provides
    @DashboardScope
    IDashboardInteractor provideDashboardInteractor(IDashboardRepository dashboardRepository,
                                                    ITokenRepository tokenRepository) {
        return new DashboardInteractor(dashboardRepository, tokenRepository);
    }

    @Provides
    @DashboardScope
    ICalendarInteractor provideCalendarInteractor(ICalendarRepository calendarRepository,
                                                  IDashboardRepository dashboardRepository,
                                                  ITokenRepository tokenRepository) {
        return new CalendarInteractor(calendarRepository, dashboardRepository, tokenRepository);
    }

    @Provides
    @DashboardScope
    IDashboardMainPresenter provideDashboardPresenter(IDashboardInteractor dashboardInteractor, ICalendarInteractor calendarInteractor) {
        return new DashboardMainPresenter(dashboardInteractor, calendarInteractor);
    }

    @Provides
    @DashboardScope
    IAddCalendarEventPresenter provideAddCalendarEventPresenter(IDashboardInteractor dashboardInteractor) {
        return new AddCalendarEventPresenter(dashboardInteractor);
    }

    @Provides
    @DashboardScope
    ISettingsPresenter provideSettingsPresenter(IDashboardInteractor dashboardInteractor) {
        return new SettingsPresenter(dashboardInteractor);
    }

    @Provides
    @DashboardScope
    ICalendarPresenter provideCalendarPresenter(IDashboardInteractor dashboardInteractor, ICalendarInteractor calendarInteractor) {
        return new CalendarPresenter(dashboardInteractor, calendarInteractor);
    }
}
