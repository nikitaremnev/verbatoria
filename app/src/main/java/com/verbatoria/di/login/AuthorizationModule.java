package com.verbatoria.di.login;

import com.verbatoria.business.login.AuthorizationInteractor;
import com.verbatoria.business.login.AuthorizationInteractorImpl;
import com.verbatoria.data.repositories.login.ILoginRepository;
import com.verbatoria.data.repositories.login.LoginRepository;
import com.verbatoria.ui.recovery_password.IRecoveryPresenter;
import com.verbatoria.ui.recovery_password.RecoveryPresenter;
import com.verbatoria.ui.sms_login.SMSConfirmationPresenter;
import com.verbatoria.ui.sms_login.SMSConfirmationPresenterImpl;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

/**
 * Модуль даггера для логина
 *
 * @author nikitaremnev
 */
@Module
public class AuthorizationModule {

    @Provides
    ILoginRepository provideLoginRepository() {
        return new LoginRepository();
    }

    @Provides
    AuthorizationInteractor provideAuthorizationInteractor(ILoginRepository loginRepository) {
        return new AuthorizationInteractorImpl(loginRepository);
    }

    @Provides
    @Reusable
    IRecoveryPresenter provideRecoveryPresenter(AuthorizationInteractor loginInteractor) {
        return new RecoveryPresenter(loginInteractor);
    }

    @Provides
    @Reusable
    SMSConfirmationPresenter provideSMSConfirmationPresenter(AuthorizationInteractor loginInteractor) {
        return new SMSConfirmationPresenterImpl(loginInteractor);
    }

}
