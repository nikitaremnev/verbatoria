package com.verbatoria.di.login;

import com.verbatoria.presentation.login.view.login.LoginActivity;
import com.verbatoria.presentation.login.view.recovery.RecoveryActivity;
import com.verbatoria.presentation.login.view.sms.SMSConfirmationActivity;

import dagger.Subcomponent;

/**
 * Компонент Даггера для логина
 *
 * @author nikitaremnev
 */
@Subcomponent(modules = {LoginModule.class})
@LoginScope
public interface LoginComponent {

    void inject(LoginActivity loginActivity);

    void inject(RecoveryActivity recoveryActivity);

    void inject(SMSConfirmationActivity smsConfirmationActivity);

}
