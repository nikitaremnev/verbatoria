package com.verbatoria.di.calendar;

import com.verbatoria.business.calendar.CalendarInteractor;
import com.verbatoria.business.calendar.ICalendarInteractor;
import com.verbatoria.business.children.ChildrenInteractor;
import com.verbatoria.business.children.IChildrenInteractor;
import com.verbatoria.business.clients.ClientsInteractor;
import com.verbatoria.business.clients.IClientsInteractor;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.business.session.Session;
import com.verbatoria.data.repositories.calendar.CalendarRepository;
import com.verbatoria.data.repositories.calendar.ICalendarRepository;
import com.verbatoria.data.repositories.children.ChildrenRepository;
import com.verbatoria.data.repositories.children.IChildrenRepository;
import com.verbatoria.data.repositories.clients.ClientsRepository;
import com.verbatoria.data.repositories.clients.IClientsRepository;
import com.verbatoria.data.repositories.dashboard.DashboardRepository;
import com.verbatoria.data.repositories.dashboard.IDashboardRepository;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.session.SessionRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.presentation.calendar.presenter.add.children.ChildrenPresenter;
import com.verbatoria.presentation.calendar.presenter.add.children.IChildrenPresenter;
import com.verbatoria.presentation.calendar.presenter.add.clients.ClientsPresenter;
import com.verbatoria.presentation.calendar.presenter.add.clients.IClientsPresenter;
import com.verbatoria.presentation.calendar.presenter.detail.EventDetailPresenter;
import com.verbatoria.presentation.calendar.presenter.detail.IEventDetailPresenter;

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
    ICalendarInteractor provideCalendarInteractor(ICalendarRepository calendarRepository,
                                                  IDashboardRepository dashboardRepository,
                                                  ITokenRepository tokenRepository) {
        return new CalendarInteractor(calendarRepository, dashboardRepository, tokenRepository);
    }

    @Provides
    @CalendarScope
    IChildrenInteractor provideChildrenInteractor(IChildrenRepository childrenRepository, ITokenRepository tokenRepository) {
        return new ChildrenInteractor(childrenRepository, tokenRepository);
    }

    @Provides
    @CalendarScope
    IClientsInteractor provideClientsInteractor(IClientsRepository clientsRepository, ITokenRepository tokenRepository) {
        return new ClientsInteractor(clientsRepository, tokenRepository);
    }

    @Provides
    @CalendarScope
    IChildrenPresenter provideChildrenPresenter(IChildrenInteractor childrenInteractor) {
        return new ChildrenPresenter(childrenInteractor);
    }

    @Provides
    @CalendarScope
    IClientsPresenter provideClientsPresenter(IClientsInteractor clientsInteractor) {
        return new ClientsPresenter(clientsInteractor);
    }

    @Provides
    @CalendarScope
    ISessionRepository provideSessionRepository() {
        return new SessionRepository();
    }

    @Provides
    @CalendarScope
    ISessionInteractor provideSessionInteractor(ISessionRepository sessionRepository, ITokenRepository tokenRepository) {
        return new Session(sessionRepository, tokenRepository);
    }

    @Provides
    @CalendarScope
    IEventDetailPresenter provideCalendarEventDetailPresenter(ISessionInteractor sessionInteractor, ICalendarInteractor calendarInteractor, IClientsInteractor clientsInteractor) {
        return new EventDetailPresenter(sessionInteractor, calendarInteractor, clientsInteractor);
    }
}
