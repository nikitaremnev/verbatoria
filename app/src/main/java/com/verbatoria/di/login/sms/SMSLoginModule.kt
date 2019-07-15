package com.verbatoria.di.login.sms

import com.verbatoria.business.login.sms.SMSLoginInteractor
import com.verbatoria.business.login.sms.SMSLoginInteractorImpl
import com.verbatoria.domain.authorization.AuthorizationManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.ui.login.sms.SMSLoginPresenter
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class SMSLoginModule {

    @Provides
    fun provideSMSLoginInteractor(
        authorizationManager: AuthorizationManager,
        schedulersFactory: RxSchedulersFactory
    ): SMSLoginInteractor =
        SMSLoginInteractorImpl(authorizationManager, schedulersFactory)

    @Provides
    @Reusable
    fun provideSMSLoginPresenter(
        phoneFromLogin: String,
        smsLoginInteractor: SMSLoginInteractor
    ): SMSLoginPresenter =
        SMSLoginPresenter(phoneFromLogin, smsLoginInteractor)

}