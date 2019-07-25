package com.verbatoria.di.calendar;

import com.verbatoria.business.calendar.CalendarInteractor;
import com.verbatoria.business.calendar.ICalendarInteractor;
import com.verbatoria.business.children.ChildrenInteractor;
import com.verbatoria.business.children.IChildrenInteractor;
import com.verbatoria.business.clients.ClientsInteractor;
import com.verbatoria.business.clients.IClientsInteractor;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.business.session.SessionInteractor;
import com.verbatoria.data.repositories.calendar.CalendarRepository;
import com.verbatoria.data.repositories.calendar.ICalendarRepository;
import com.verbatoria.data.repositories.children.ChildrenRepository;
import com.verbatoria.data.repositories.children.IChildrenRepository;
import com.verbatoria.data.repositories.clients.ClientsRepository;
import com.verbatoria.data.repositories.clients.IClientsRepository;
import com.verbatoria.data.repositories.dashboard.DashboardRepository;
import com.verbatoria.data.repositories.dashboard.IDashboardRepository;
import com.verbatoria.data.repositories.schedule.IScheduleRepository;
import com.verbatoria.data.repositories.schedule.ScheduleRepository;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.session.SessionRepository;
import com.verbatoria.ui.calendar.presenter.add.children.ChildPresenter;
import com.verbatoria.ui.calendar.presenter.add.children.IChildPresenter;
import com.verbatoria.ui.calendar.presenter.add.clients.ClientsPresenter;
import com.verbatoria.ui.calendar.presenter.add.clients.IClientsPresenter;
import com.verbatoria.ui.calendar.presenter.detail.EventDetailPresenter;
import com.verbatoria.ui.calendar.presenter.detail.IEventDetailPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Модуль даггера для календаря
 *
 * @author nikitaremnev
 */
@Module
public class CalendarModule {

    @Provides
    @CalendarScope
    IChildrenRepository provideChildrenRepository() {
        return new ChildrenRepository();
    }

    @Provides
    @CalendarScope
    IClientsRepository provideClientsRepository() {
        return new ClientsRepository();
    }

    @Provides
    @CalendarScope
    IDashboardRepository provideDashboardRepository() {
        return new DashboardRepository();
    }

    @Provides
    @CalendarScope
    ICalendarRepository provideCalendarRepository() {
        return new CalendarRepository();
    }

    @Provides
    @CalendarScope
    IScheduleRepository provideScheduleRepository() {
        return new ScheduleRepository();
    }

    @Provides
    @CalendarScope
    ICalendarInteractor provideCalendarInteractor(ICalendarRepository calendarRepository,
                                                  IScheduleRepository scheduleRepository,
                                                  IDashboardRepository dashboardRepository) {
        return new CalendarInteractor(calendarRepository, scheduleRepository, dashboardRepository);
    }

    @Provides
    @CalendarScope
    IChildrenInteractor provideChildrenInteractor(IChildrenRepository childrenRepository) {
        return new ChildrenInteractor(childrenRepository);
    }

    @Provides
    @CalendarScope
    IClientsInteractor provideClientsInteractor(IClientsRepository clientsRepository) {
        return new ClientsInteractor(clientsRepository);
    }

    @Provides
    @CalendarScope
    IChildPresenter provideChildrenPresenter(IChildrenInteractor childrenInteractor, ISessionRepository sessionRepository) {
        return new ChildPresenter(childrenInteractor, sessionRepository.isSchoolAccount());
    }

    @Provides
    @CalendarScope
    IClientsPresenter provideClientsPresenter(IClientsInteractor clientsInteractor) {
        return new ClientsPresenter(clientsInteractor);
    }
//
//    @Provides
//    @CalendarScope
//    IDashboardInteractor provideDashboardInteractor(IDashboardRepository dashboardRepository,
//                                                    ITokenRepository tokenRepository,
//                                                    ISessionRepository sessionRepository) {
//        return new DashboardInteractor(dashboardRepository, tokenRepository, sessionRepository);
//    }

    @Provides
    @CalendarScope
    ISessionRepository provideSessionRepository() {
        return new SessionRepository();
    }

    @Provides
    @CalendarScope
    ISessionInteractor provideSessionInteractor(ISessionRepository sessionRepository, ICalendarRepository calendarRepository) {
        return new SessionInteractor(sessionRepository, calendarRepository);
    }

    @Provides
    @CalendarScope
    IEventDetailPresenter provideCalendarEventDetailPresenter(ISessionInteractor sessionInteractor, ICalendarInteractor calendarInteractor, IClientsInteractor clientsInteractor) {
        return new EventDetailPresenter(sessionInteractor, calendarInteractor, clientsInteractor, sessionInteractor.isSchoolAccount());
    }
}
