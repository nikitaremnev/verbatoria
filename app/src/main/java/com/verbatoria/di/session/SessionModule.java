package com.verbatoria.di.session;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.business.login.ILoginInteractor;
import com.verbatoria.business.login.LoginInteractor;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.business.session.SessionInteractor;
import com.verbatoria.data.repositories.login.ILoginRepository;
import com.verbatoria.data.repositories.login.LoginRepository;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.session.SessionRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.di.dashboard.DashboardScope;
import com.verbatoria.di.login.LoginScope;
import com.verbatoria.presentation.dashboard.presenter.calendar.detail.CalendarEventDetailPresenter;
import com.verbatoria.presentation.dashboard.presenter.calendar.detail.ICalendarEventDetailPresenter;
import com.verbatoria.presentation.login.presenter.ILoginPresenter;
import com.verbatoria.presentation.login.presenter.LoginPresenter;

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

}
