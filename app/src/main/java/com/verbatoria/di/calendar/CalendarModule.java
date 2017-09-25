package com.verbatoria.di.calendar;

import com.verbatoria.business.login.ILoginInteractor;
import com.verbatoria.business.login.LoginInteractor;
import com.verbatoria.data.repositories.login.ILoginRepository;
import com.verbatoria.data.repositories.login.LoginRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.di.login.LoginScope;
import com.verbatoria.presentation.login.presenter.login.ILoginPresenter;
import com.verbatoria.presentation.login.presenter.login.LoginPresenter;
import com.verbatoria.presentation.login.presenter.recovery.IRecoveryPresenter;
import com.verbatoria.presentation.login.presenter.recovery.RecoveryPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Модуль даггера для логина
 *
 * @author nikitaremnev
 */
@Module
public class CalendarModule {

//    @Provides
//    @LoginScope
//    ILoginRepository provideLoginRepository() {
//        return new LoginRepository();
//    }
//
//    @Provides
//    @LoginScope
//    ILoginInteractor provideLoginInteractor(ILoginRepository loginRepository, ITokenRepository tokenRepository) {
//        return new LoginInteractor(loginRepository, tokenRepository);
//    }
//
//    @Provides
//    @LoginScope
//    ILoginPresenter provideLoginPresenter(ILoginInteractor loginInteractor) {
//        return new LoginPresenter(loginInteractor);
//    }
//
//    @Provides
//    @LoginScope
//    IRecoveryPresenter provideRecoveryPresenter(ILoginInteractor loginInteractor) {
//        return new RecoveryPresenter(loginInteractor);
//    }

}
