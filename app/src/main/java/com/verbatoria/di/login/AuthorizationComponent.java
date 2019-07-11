package com.verbatoria.di.login;

import com.verbatoria.ui.recovery_password.RecoveryActivity;
import com.verbatoria.ui.sms_login.SMSConfirmationActivity;

import dagger.Subcomponent;

/**
 * Компонент Даггера для логина
 *
 * @author nikitaremnev
 */
@Subcomponent(modules = {AuthorizationModule.class})
@LoginScope
public interface AuthorizationComponent {

    void inject(RecoveryActivity recoveryActivity);

    void inject(SMSConfirmationActivity smsConfirmationActivity);

}
