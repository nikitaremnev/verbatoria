package com.verbatoria.di.dashboard;

import com.verbatoria.business.calendar.CalendarInteractor;
import com.verbatoria.business.calendar.ICalendarInteractor;
import com.verbatoria.business.dashboard.DashboardInteractor;
import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.data.repositories.calendar.CalendarRepository;
import com.verbatoria.data.repositories.calendar.ICalendarRepository;
import com.verbatoria.data.repositories.dashboard.DashboardRepository;
import com.verbatoria.data.repositories.dashboard.IDashboardRepository;
import com.verbatoria.data.repositories.schedule.IScheduleRepository;
import com.verbatoria.data.repositories.schedule.ScheduleRepository;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.session.SessionRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.presentation.calendar.presenter.CalendarPresenter;
import com.verbatoria.presentation.calendar.presenter.ICalendarPresenter;
import com.verbatoria.presentation.calendar.presenter.add.AddCalendarEventPresenter;
import com.verbatoria.presentation.calendar.presenter.add.IAddCalendarEventPresenter;
import com.verbatoria.presentation.dashboard.presenter.DashboardPresenter;
import com.verbatoria.presentation.dashboard.presenter.IDashboardPresenter;
import com.verbatoria.presentation.dashboard.presenter.main.IVerbatologInfoPresenter;
import com.verbatoria.presentation.dashboard.presenter.main.VerbatologInfoPresenter;
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
    IScheduleRepository provideScheduleRepository() {
        return new ScheduleRepository();
    }

    @Provides
    @DashboardScope
    ISessionRepository provideSessionRepository() {
        return new SessionRepository();
    }

    @Provides
    @DashboardScope
    IDashboardInteractor provideDashboardInteractor(IDashboardRepository dashboardRepository,
                                                    ITokenRepository tokenRepository,
                                                    ISessionRepository sessionRepository) {
        return new DashboardInteractor(dashboardRepository, tokenRepository, sessionRepository);
    }

    @Provides
    @DashboardScope
    ICalendarInteractor provideCalendarInteractor(ICalendarRepository calendarRepository,
                                                  IScheduleRepository scheduleRepository,
                                                  IDashboardRepository dashboardRepository,
                                                  ITokenRepository tokenRepository) {
        return new CalendarInteractor(calendarRepository, scheduleRepository, dashboardRepository, tokenRepository);
    }

    @Provides
    @DashboardScope
    IDashboardPresenter provideDashboardPresenter(IDashboardInteractor dashboardInteractor) {
        return new DashboardPresenter(dashboardInteractor);
    }

    @Provides
    @DashboardScope
    IVerbatologInfoPresenter provideVerbatologInfoPresenter(IDashboardInteractor dashboardInteractor) {
        return new VerbatologInfoPresenter(dashboardInteractor);
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
    ICalendarPresenter provideCalendarPresenter(ICalendarInteractor calendarInteractor) {
        return new CalendarPresenter(calendarInteractor);
    }
}
