package com.verbatoria.business.login

import com.verbatoria.domain.authorization.AuthorizationManager
import com.verbatoria.utils.RxSchedulers
import io.reactivex.Completable
import io.reactivex.Single
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

interface LoginInteractor {

    fun login(phone: String, password: String): Single<Boolean>

    fun getLastLogin(): Single<String>

    fun getCurrentCountry(): Single<String>

    fun saveCurrentCountry(country: String): Completable

}

class LoginInteractorImpl(
    private val authorizationManager: AuthorizationManager
) : LoginInteractor {

    private val logger = LoggerFactory.getLogger("LoginInteractor")

    override fun login(phone: String, password: String): Single<Boolean> =
        Single.fromCallable {
            authorizationManager.login(phone, password)
        }
//        loginRepository.getLogin(getLoginRequestModel(phone, password)).map { item ->
//            val tokenProcessor = TokenProcessor()
//            val tokenModel = tokenProcessor.obtainToken(item)
//            tokenRepository.apply {
//                updateToken(tokenModel)
//                setStatus(tokenModel.status)
//            }
//            loginRepository.apply {
//                updateLastLogin(phone)
//                updateLocationId(tokenModel.locationId)
//            }
//            tokenModel
//        }
            .subscribeOn(RxSchedulers.getNewThreadScheduler())
            .observeOn(RxSchedulers.getMainThreadScheduler())

    override fun getLastLogin(): Single<String> =
        Single.fromCallable {
            authorizationManager.getLastLogin()
        }
            .subscribeOn(RxSchedulers.getNewThreadScheduler())
            .observeOn(RxSchedulers.getMainThreadScheduler())

    override fun getCurrentCountry(): Single<String> =
        Single.fromCallable {
            authorizationManager.getCurrentCountry()
        }
            .subscribeOn(RxSchedulers.getNewThreadScheduler())
            .observeOn(RxSchedulers.getMainThreadScheduler())

    override fun saveCurrentCountry(country: String): Completable =
        Completable.fromCallable {
            authorizationManager.saveCurrentCountry(country)
        }
            .subscribeOn(RxSchedulers.getNewThreadScheduler())
            .observeOn(RxSchedulers.getMainThreadScheduler())



}