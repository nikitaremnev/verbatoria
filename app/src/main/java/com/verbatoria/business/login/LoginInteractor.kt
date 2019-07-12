package com.verbatoria.business.login

import com.verbatoria.domain.authorization.AuthorizationManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

interface LoginInteractor {

    fun login(phone: String, password: String): Completable

    fun getLastLogin(): Single<String>

    fun getCurrentCountry(): Single<String>

    fun saveCurrentCountry(country: String): Completable

}

class LoginInteractorImpl(
    private val authorizationManager: AuthorizationManager,
    private val schedulersFactory: RxSchedulersFactory
) : LoginInteractor {

    private val logger = LoggerFactory.getLogger("LoginInteractor")

    override fun login(phone: String, password: String): Completable =
        Completable.fromCallable {
            authorizationManager.login(phone, password)
            authorizationManager.saveLastLogin(phone)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun getLastLogin(): Single<String> =
        Single.fromCallable {
            authorizationManager.getLastLogin()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun getCurrentCountry(): Single<String> =
        Single.fromCallable {
            authorizationManager.getCurrentCountry()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun saveCurrentCountry(country: String): Completable =
        Completable.fromCallable {
            authorizationManager.saveCurrentCountry(country)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}