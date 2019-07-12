package com.verbatoria.business.login.sms

import com.verbatoria.domain.authorization.AuthorizationManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Single
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

interface SMSLoginInteractor {

    fun getCurrentCountry(): Single<String>

}

class SMSLoginInteractorImpl(
    private val authorizationManager: AuthorizationManager,
    private val schedulersFactory: RxSchedulersFactory
) : SMSLoginInteractor {

    private val logger = LoggerFactory.getLogger("SMSLoginInteractor")

    override fun getCurrentCountry(): Single<String> =
        Single.fromCallable {
            authorizationManager.getCurrentCountry()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}