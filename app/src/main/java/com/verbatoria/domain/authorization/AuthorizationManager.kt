package com.verbatoria.domain.authorization

import com.verbatoria.infrastructure.retrofit.endpoints.authorization.AuthorizationEndpoint
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

    fun getLastLogin(): String

    fun saveLastLogin(phone: String)

    fun getCurrentCountry(): String

    fun saveCurrentCountry(country: String)

}

class AuthorizationManagerImpl(
    private val authorizationEndpoint: AuthorizationEndpoint,
    private val authorizationRepository: AuthorizationRepository
) : AuthorizationManager {

    override fun login(phone: String, password: String) {
        val response = authorizationEndpoint.login(
            LoginParamsDto(
                phone = processPhone(phone),
                password = password
            )
        )
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

    override fun getLastLogin(): String =
        authorizationRepository.getLastLogin()

    override fun saveLastLogin(phone: String) {
        authorizationRepository.putLastLogin(phone)
    }

    override fun getCurrentCountry(): String =
        authorizationRepository.getCurrentCountry()

    override fun saveCurrentCountry(country: String) {
        authorizationRepository.putCurrentCountry(country)
    }

    private fun processPhone(phone: String): String =
        "+" + phone.replace("[-,. )(]".toRegex(), "")

}