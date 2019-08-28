package com.verbatoria.di.session;

import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.business.session.SessionInteractor;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.session.SessionRepository;
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
    ISessionRepository provideSessionRepository() {
        return new SessionRepository();
    }

    @Provides
    @SessionScope
    ISessionInteractor provideSessionInteractor(ISessionRepository sessionRepository) {
        return new SessionInteractor(sessionRepository);
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

}
