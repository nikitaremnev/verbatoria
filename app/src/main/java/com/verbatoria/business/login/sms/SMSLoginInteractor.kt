package com.verbatoria.business.login.sms

import com.verbatoria.domain.authorization.manager.AuthorizationManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Single

/**
 * @author n.remnev
 */

interface SMSLoginInteractor {

    fun sendSMSCode(phone: String, smsText: String): Single<String>

    fun getCurrentPhone(): Single<String>

}

class SMSLoginInteractorImpl(
    private val authorizationManager: AuthorizationManager,
    private val schedulersFactory: RxSchedulersFactory
) : SMSLoginInteractor {

    override fun sendSMSCode(phone: String, smsText: String): Single<String> =
        Single.fromCallable {
            authorizationManager.sendSMSCode(phone, smsText)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun getCurrentPhone(): Single<String> =
        Single.fromCallable {
            authorizationManager.getLastLogin()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}