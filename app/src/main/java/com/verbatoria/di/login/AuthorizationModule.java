package com.verbatoria.di.login;

import com.verbatoria.business.login.AuthorizationInteractor;
import com.verbatoria.business.login.AuthorizationInteractorImpl;
import com.verbatoria.data.repositories.login.ILoginRepository;
import com.verbatoria.data.repositories.login.LoginRepository;
import com.verbatoria.ui.login.presenter.recovery.IRecoveryPresenter;
import com.verbatoria.ui.login.presenter.recovery.RecoveryPresenter;
import com.verbatoria.ui.login.presenter.sms.SMSConfirmationPresenter;
import com.verbatoria.ui.login.presenter.sms.SMSConfirmationPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Модуль даггера для логина
 *
 * @author nikitaremnev
 */
@Module
public class AuthorizationModule {

    @Provides
    @LoginScope
    ILoginRepository provideLoginRepository() {
        return new LoginRepository();
    }

    @Provides
    @LoginScope
    AuthorizationInteractor provideAuthorizationInteractor(ILoginRepository loginRepository) {
        return new AuthorizationInteractorImpl(loginRepository);
    }

    @Provides
    @LoginScope
    IRecoveryPresenter provideRecoveryPresenter(AuthorizationInteractor loginInteractor) {
        return new RecoveryPresenter(loginInteractor);
    }

    @Provides
    @LoginScope
    SMSConfirmationPresenter provideSMSConfirmationPresenter(AuthorizationInteractor loginInteractor) {
        return new SMSConfirmationPresenterImpl(loginInteractor);
    }

}
