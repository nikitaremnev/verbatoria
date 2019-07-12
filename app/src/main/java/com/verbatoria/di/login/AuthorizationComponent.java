package com.verbatoria.di.login;

import com.verbatoria.ui.sms_login.SMSConfirmationActivity;

import dagger.Subcomponent;

/**
 * @author nikitaremnev
 */

@Subcomponent(modules = {AuthorizationModule.class})
public interface AuthorizationComponent {

    void inject(SMSConfirmationActivity smsConfirmationActivity);

}
