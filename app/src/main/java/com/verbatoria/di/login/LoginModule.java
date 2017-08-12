package com.verbatoria.di.login;

import com.verbatoria.business.login.ILoginInteractor;
import com.verbatoria.business.login.LoginInteractor;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.business.session.SessionInteractor;
import com.verbatoria.data.repositories.login.ILoginRepository;
import com.verbatoria.data.repositories.login.LoginRepository;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.session.SessionRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.di.session.SessionScope;
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
public class LoginModule {

    @Provides
    @LoginScope
    ILoginRepository provideLoginRepository() {
        return new LoginRepository();
    }

    @Provides
    @LoginScope
    ILoginInteractor provideLoginInteractor(ILoginRepository loginRepository, ITokenRepository tokenRepository) {
        return new LoginInteractor(loginRepository, tokenRepository);
    }

    @Provides
    @LoginScope
    ILoginPresenter provideLoginPresenter(ILoginInteractor loginInteractor) {
        return new LoginPresenter(loginInteractor);
    }

}
