package com.verbatoria.di.session;

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
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.session.SessionRepository;
import com.verbatoria.ui.calendar.presenter.detail.EventDetailPresenter;
import com.verbatoria.ui.calendar.presenter.detail.IEventDetailPresenter;
import com.verbatoria.ui.session.presenter.connection.ConnectionPresenter;
import com.verbatoria.ui.session.presenter.connection.IConnectionPresenter;
import com.verbatoria.ui.session.presenter.reconnect.IReconnectionPresenter;
import com.verbatoria.ui.session.presenter.reconnect.ReconnectionPresenter;
import com.verbatoria.ui.session.presenter.submit.ISubmitPresenter;
import com.verbatoria.ui.session.presenter.submit.SubmitPresenter;
import com.verbatoria.ui.session.presenter.submit.school.ISchoolSubmitPresenter;
import com.verbatoria.ui.session.presenter.submit.school.SchoolSubmitPresenter;
import com.verbatoria.ui.session.presenter.writing.IWritingPresenter;
import com.verbatoria.ui.session.presenter.writing.WritingPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Модуль даггера для логина
 *
 * @author nikitaremnev
 */
@Module
public class SessionModule {

    @Provides
    @SessionScope
    IChildrenRepository provideChildrenRepository() {
        return new ChildrenRepository();
    }

    @Provides
    @SessionScope
    IClientsRepository provideClientsRepository() {
        return new ClientsRepository();
    }

    @Provides
    @SessionScope
    IChildrenInteractor provideChildrenInteractor(IChildrenRepository childrenRepository) {
        return new ChildrenInteractor(childrenRepository);
    }

    @Provides
    @SessionScope
    IClientsInteractor provideClientsInteractor(IClientsRepository clientsRepository) {
        return new ClientsInteractor(clientsRepository);
    }

    @Provides
    @SessionScope
    ICalendarRepository provideCalendarRepository() {
        return new CalendarRepository();
    }

    @Provides
    @SessionScope
    ISessionRepository provideSessionRepository() {
        return new SessionRepository();
    }

    @Provides
    @SessionScope
    ISessionInteractor provideSessionInteractor(ISessionRepository sessionRepository, ICalendarRepository calendarRepository) {
        return new SessionInteractor(sessionRepository, calendarRepository);
    }

    @Provides
    @SessionScope
    IEventDetailPresenter provideCalendarEventDetailPresenter(ISessionInteractor sessionInteractor, ICalendarInteractor calendarInteractor, IClientsInteractor clientsInteractor) {
        return new EventDetailPresenter(sessionInteractor, calendarInteractor, clientsInteractor, sessionInteractor.isSchoolAccount());
    }

    @Provides
    @SessionScope
    IConnectionPresenter provideConnectionPresenter(ISessionInteractor sessionInteractor) {
        return new ConnectionPresenter(sessionInteractor);
    }

    @Provides
    @SessionScope
    IReconnectionPresenter provideReconnectionPresenter(ISessionInteractor sessionInteractor) {
        return new ReconnectionPresenter(sessionInteractor);
    }

    @Provides
    @SessionScope
    IWritingPresenter provideWritingPresenter(ISessionInteractor sessionInteractor) {
        return new WritingPresenter(sessionInteractor);
    }

    @Provides
    @SessionScope
    ISubmitPresenter provideSubmitPresenter(ISessionInteractor sessionInteractor) {
        return new SubmitPresenter(sessionInteractor);
    }

    @Provides
    @SessionScope
    ISchoolSubmitPresenter provideSchoolSubmitPresenter(ISessionInteractor sessionInteractor) {
        return new SchoolSubmitPresenter(sessionInteractor);
    }

//    @Provides
//    @SessionScope
//    IDashboardInteractor provideDashboardInteractor(IDashboardRepository dashboardRepository,
//                                                    ITokenRepository tokenRepository,
//                                                    ISessionRepository sessionRepository) {
//        return new DashboardInteractor(dashboardRepository, tokenRepository, sessionRepository);
//    }
}
