package com.verbatoria.business.login

import com.verbatoria.business.token.models.TokenModel
import com.verbatoria.business.token.processor.TokenProcessor
import com.verbatoria.data.network.request.LoginRequestModel
import com.verbatoria.data.repositories.login.ILoginRepository
import com.verbatoria.data.repositories.token.ITokenRepository
import com.verbatoria.utils.PreferencesStorage
import com.verbatoria.utils.RxSchedulers
import io.reactivex.Observable
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

interface LoginInteractor {

    fun login(phone: String, password: String): Observable<TokenModel>

    fun getLastLogin(): String

    fun saveCountrySelection(country: String)

    fun getCountry(): String

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
            tokenRepository.updateToken(tokenModel)
            tokenRepository.setStatus(tokenModel.status)
            loginRepository.updateLastLogin(phone)
            loginRepository.updateLocationId(tokenModel.locationId)
            tokenModel
        }
            .subscribeOn(RxSchedulers.getNewThreadScheduler())
            .observeOn(RxSchedulers.getMainThreadScheduler())

    override fun getLastLogin(): String =
        loginRepository.lastLogin()

    override fun saveCountrySelection(country: String) {
        PreferencesStorage.getInstance().country = country
    }

    override fun getCountry(): String =
        PreferencesStorage.getInstance().country

    private fun getLoginRequestModel(phone: String, password: String): LoginRequestModel =
        LoginRequestModel()
            .setPhone(processPhone(phone))
            .setPassword(password)

    private fun processPhone(phone: String): String =
        phone.replace("[-,. )(]".toRegex(), "")

}