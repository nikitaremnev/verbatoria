package com.verbatoria.business.login

import com.verbatoria.business.token.models.TokenModel
import com.verbatoria.business.token.processor.TokenProcessor
import com.verbatoria.data.network.request.LoginRequestModel
import com.verbatoria.data.repositories.login.ILoginRepository
import com.verbatoria.data.repositories.token.ITokenRepository
import com.verbatoria.utils.PreferencesStorage
import com.verbatoria.utils.RxSchedulers
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

interface LoginInteractor {

    fun login(phone: String, password: String): Observable<TokenModel>

    fun getLastLogin(): Single<String>

    fun getCurrentCountry(): Single<String>

    fun saveCurrentCountry(country: String): Completable

}

class LoginInteractorImpl(
    private val tokenRepository: ITokenRepository,
    private val loginRepository: ILoginRepository
) : LoginInteractor {

    private val logger = LoggerFactory.getLogger("LoginInteractor")

    override fun login(phone: String, password: String): Observable<TokenModel> =
        loginRepository.getLogin(getLoginRequestModel(phone, password)).map { item ->
            val tokenProcessor = TokenProcessor()
            val tokenModel = tokenProcessor.obtainToken(item)
            tokenRepository.apply {
                updateToken(tokenModel)
                setStatus(tokenModel.status)
            }
            loginRepository.apply {
                updateLastLogin(phone)
                updateLocationId(tokenModel.locationId)
            }
            tokenModel
        }
            .subscribeOn(RxSchedulers.getNewThreadScheduler())
            .observeOn(RxSchedulers.getMainThreadScheduler())

    override fun getLastLogin(): Single<String> =
        Single.fromCallable {
            loginRepository.lastLogin()
        }
            .subscribeOn(RxSchedulers.getNewThreadScheduler())
            .observeOn(RxSchedulers.getMainThreadScheduler())

    override fun getCurrentCountry(): Single<String> =
        Single.fromCallable {
            PreferencesStorage.getInstance().country
        }
            .subscribeOn(RxSchedulers.getNewThreadScheduler())
            .observeOn(RxSchedulers.getMainThreadScheduler())

    override fun saveCurrentCountry(country: String): Completable =
        Completable.fromCallable {
            PreferencesStorage.getInstance().setCountry(country)
        }
            .subscribeOn(RxSchedulers.getNewThreadScheduler())
            .observeOn(RxSchedulers.getMainThreadScheduler())

    private fun getLoginRequestModel(phone: String, password: String): LoginRequestModel =
        LoginRequestModel()
            .setPhone(processPhone(phone))
            .setPassword(password)

    private fun processPhone(phone: String): String =
        phone.replace("[-,. )(]".toRegex(), "")

}