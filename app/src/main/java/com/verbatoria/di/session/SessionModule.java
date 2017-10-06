package com.verbatoria.di.session;

import com.verbatoria.business.calendar.ICalendarInteractor;
import com.verbatoria.business.children.ChildrenInteractor;
import com.verbatoria.business.children.IChildrenInteractor;
import com.verbatoria.business.clients.ClientsInteractor;
import com.verbatoria.business.clients.IClientsInteractor;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.business.session.Session;
import com.verbatoria.data.repositories.children.ChildrenRepository;
import com.verbatoria.data.repositories.children.IChildrenRepository;
import com.verbatoria.data.repositories.clients.ClientsRepository;
import com.verbatoria.data.repositories.clients.IClientsRepository;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.session.SessionRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.presentation.calendar.presenter.detail.EventDetailPresenter;
import com.verbatoria.presentation.calendar.presenter.detail.IEventDetailPresenter;
import com.verbatoria.presentation.session.presenter.connection.ConnectionPresenter;
import com.verbatoria.presentation.session.presenter.connection.IConnectionPresenter;
import com.verbatoria.presentation.session.presenter.reconnect.IReconnectionPresenter;
import com.verbatoria.presentation.session.presenter.reconnect.ReconnectionPresenter;
import com.verbatoria.presentation.session.presenter.submit.ISubmitPresenter;
import com.verbatoria.presentation.session.presenter.submit.SubmitPresenter;
import com.verbatoria.presentation.session.presenter.writing.IWritingPresenter;
import com.verbatoria.presentation.session.presenter.writing.WritingPresenter;

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
    IChildrenInteractor provideChildrenInteractor(IChildrenRepository childrenRepository, ITokenRepository tokenRepository) {
        return new ChildrenInteractor(childrenRepository, tokenRepository);
    }

    @Provides
    @SessionScope
    IClientsInteractor provideClientsInteractor(IClientsRepository clientsRepository, ITokenRepository tokenRepository) {
        return new ClientsInteractor(clientsRepository, tokenRepository);
    }

    @Provides
    @SessionScope
    ISessionRepository provideSessionRepository() {
        return new SessionRepository();
    }

    @Provides
    @SessionScope
    ISessionInteractor provideSessionInteractor(ISessionRepository sessionRepository, ITokenRepository tokenRepository) {
        return new Session(sessionRepository, tokenRepository);
    }

    @Provides
    @SessionScope
    IEventDetailPresenter provideCalendarEventDetailPresenter(ISessionInteractor sessionInteractor, ICalendarInteractor calendarInteractor, IClientsInteractor clientsInteractor) {
        return new EventDetailPresenter(sessionInteractor, calendarInteractor, clientsInteractor);
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
}
