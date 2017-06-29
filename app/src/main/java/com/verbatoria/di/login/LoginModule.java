package com.verbatoria.di.login;

import com.verbatoria.business.login.ILoginInteractor;
import com.verbatoria.business.login.LoginInteractor;
import com.verbatoria.business.token.interactor.ITokenInteractor;
import com.verbatoria.data.repositories.login.ILoginRepository;
import com.verbatoria.data.repositories.login.LoginRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.presentation.login.presenter.ILoginPresenter;
import com.verbatoria.presentation.login.presenter.LoginPresenter;
import com.verbatoria.utils.rx.IRxSchedulers;

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
    ILoginPresenter provideLoginPresenter(ILoginInteractor loginInteractor,
                                          IRxSchedulers rxSchedulers) {
        return new LoginPresenter(loginInteractor, rxSchedulers);
    }

}
