package com.verbatoria.domain.authorization

import com.verbatoria.domain.authorization.model.OnlineAuthorization
import com.verbatoria.domain.dashboard.info.InfoRepository
import com.verbatoria.domain.dashboard.settings.SettingsRepository
import com.verbatoria.domain.session.SessionManager
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.AuthorizationEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.SMSLoginEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.params.LoginParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.params.RecoveryPasswordParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.params.ResetPasswordParamsDto

/**
 * @author n.remnev
 */

interface AuthorizationManager {

    fun login(phone: String, password: String)

    fun recoveryPassword(phone: String)

    fun resetPassword(phone: String, confirmationCode: String, password: String)

    fun sendSMSCode(phone: String, smsText: String): String

    fun getLastLogin(): String

    fun saveLastLogin(login: String)

    fun getCurrentCountry(): String

    fun saveCurrentCountry(country: String)

}

class AuthorizationManagerImpl(
    private val sessionManager: SessionManager,
    private val authorizationEndpoint: AuthorizationEndpoint,
    private val smsLoginEndpoint: SMSLoginEndpoint,
    private val authorizationRepository: AuthorizationRepository,
    private val infoRepository: InfoRepository,
    private val settingsRepository: SettingsRepository
) : AuthorizationManager {

    override fun login(phone: String, password: String) {
        val response = authorizationEndpoint.login(
            LoginParamsDto(
                phone = processPhone(phone),
                password = password
            )
        )
        infoRepository.deleteAll()
        settingsRepository.deleteAll()
        infoRepository.putLocationId(response.locationId)
        sessionManager.startSession(OnlineAuthorization(response.accessToken))
    }

    override fun recoveryPassword(phone: String) {
        authorizationEndpoint.recoveryPassword(
            RecoveryPasswordParamsDto(
                phone = processPhone(phone)
            )
        )
    }

    override fun resetPassword(phone: String, confirmationCode: String, password: String) {
        authorizationEndpoint.resetPassword(
            ResetPasswordParamsDto(
                phone = processPhone(phone),
                recoveryHash = confirmationCode,
                password = password
            )
        )
    }

    override fun sendSMSCode(phone: String, smsText: String): String {
        val response = smsLoginEndpoint.sendSmsLogin(phone, smsText)
        return response.code
    }

    override fun getLastLogin(): String =
        authorizationRepository.getLastLogin()

    override fun saveLastLogin(login: String) {
        val currentLastLogin = getLastLogin()
        if (currentLastLogin != login) {
            infoRepository.deleteAll()
            settingsRepository.deleteAll()
            authorizationRepository.putLastLogin(login)
        }
    }

    override fun getCurrentCountry(): String =
        authorizationRepository.getCurrentCountry()

    override fun saveCurrentCountry(country: String) {
        authorizationRepository.putCurrentCountry(country)
    }

    private fun processPhone(phone: String): String =
        "+" + phone.replace("[-,. )(]".toRegex(), "")

}