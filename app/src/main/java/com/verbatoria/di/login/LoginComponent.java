package com.verbatoria.di.login;

import com.verbatoria.ui.login.view.login.LoginActivity;
import com.verbatoria.ui.login.view.recovery.RecoveryActivity;
import com.verbatoria.ui.login.view.sms.SMSConfirmationActivity;

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
