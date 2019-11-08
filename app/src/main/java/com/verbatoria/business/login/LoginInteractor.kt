package com.verbatoria.business.login

import com.verbatoria.domain.authorization.manager.AuthorizationManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

interface LoginInteractor {

    fun login(phone: String, password: String): Completable

    fun getLastLoginAndCurrentCountry(): Single<Pair<String, String>>

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

    override fun getLastLoginAndCurrentCountry(): Single<Pair<String, String>> =
        Single.fromCallable {
            Pair(authorizationManager.getLastLogin(), authorizationManager.getCurrentCountry())
        }
            .subscribeOn(schedulersFactory.database)
            .observeOn(schedulersFactory.main)

    override fun saveCurrentCountry(country: String): Completable =
        Completable.fromCallable {
            authorizationManager.saveCurrentCountry(country)
        }
            .subscribeOn(schedulersFactory.database)
            .observeOn(schedulersFactory.main)

}