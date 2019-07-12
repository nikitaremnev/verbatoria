package com.verbatoria.business.recovery_password

import com.verbatoria.domain.authorization.AuthorizationManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

interface RecoveryPasswordInteractor {

    fun getCurrentCountry(): Single<String>

    fun recoveryPassword(phone: String): Completable

}

class RecoveryPasswordInteractorImpl(
    private val authorizationManager: AuthorizationManager,
    private val schedulersFactory: RxSchedulersFactory
) : RecoveryPasswordInteractor {

    private val logger = LoggerFactory.getLogger("RecoveryPasswordInteractor")

    override fun getCurrentCountry(): Single<String> =
        Single.fromCallable {
            authorizationManager.getCurrentCountry()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun recoveryPassword(phone: String): Completable =
        Completable.fromCallable {
            authorizationManager.recoveryPassword(phone)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}