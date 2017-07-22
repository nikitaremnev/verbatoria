package com.verbatoria.di.session;

import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.business.session.SessionInteractor;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.session.SessionRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.presentation.dashboard.presenter.calendar.detail.CalendarEventDetailPresenter;
import com.verbatoria.presentation.dashboard.presenter.calendar.detail.ICalendarEventDetailPresenter;
import com.verbatoria.presentation.session.presenter.connection.ConnectionPresenter;
import com.verbatoria.presentation.session.presenter.connection.IConnectionPresenter;
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
    ISessionRepository provideSessionRepository() {
        return new SessionRepository();
    }

    @Provides
    @SessionScope
    ISessionInteractor provideSessionInteractor(ISessionRepository sessionRepository, ITokenRepository tokenRepository) {
        return new SessionInteractor(sessionRepository, tokenRepository);
    }

    @Provides
    @SessionScope
    ICalendarEventDetailPresenter provideCalendarEventDetailPresenter(ISessionInteractor sessionInteractor) {
        return new CalendarEventDetailPresenter(sessionInteractor);
    }

    @Provides
    @SessionScope
    IConnectionPresenter provideConnectionPresenter(ISessionInteractor sessionInteractor) {
        return new ConnectionPresenter(sessionInteractor);
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
