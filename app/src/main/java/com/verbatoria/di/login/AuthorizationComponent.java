package com.verbatoria.di.login;

import com.verbatoria.ui.login.view.recovery.RecoveryActivity;
import com.verbatoria.ui.login.view.sms.SMSConfirmationActivity;

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
