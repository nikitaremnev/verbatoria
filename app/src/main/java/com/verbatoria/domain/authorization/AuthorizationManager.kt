package com.verbatoria.domain.authorization

import com.verbatoria.infrastructure.retrofit.endpoints.authorization.AuthorizationEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.params.LoginParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.params.RecoveryPasswordParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.params.ResetPasswordParamsDto

/**
 * @author n.remnev
 */

interface AuthorizationManager {

    fun login(phone: String, password: String): Pair<Boolean, String?>

    fun recoveryPassword(phone: String): Pair<Boolean, String?>

    fun resetPassword(phone: String, recoveryHash: String, password: String)

    fun getLastLogin(): String

    fun getCurrentCountry(): String

    fun saveCurrentCountry(country: String)

}

class AuthorizationManagerImpl(
    private val authorizationEndpoint: AuthorizationEndpoint,
    private val authorizationRepository: AuthorizationRepository
) : AuthorizationManager {

    override fun login(phone: String, password: String): Pair<Boolean, String?> {
        val response = authorizationEndpoint.login(
            LoginParamsDto(
                phone = processPhone(phone),
                password = password
            )
        )
        return Pair(response.error == null, response.error)
    }

    override fun recoveryPassword(phone: String): Pair<Boolean, String?> {
        val response = authorizationEndpoint.recoveryPassword(
            RecoveryPasswordParamsDto(
                phone = phone
            )
        )
        return Pair(response.error == null, response.error)
    }

    override fun resetPassword(phone: String, recoveryHash: String, password: String) {
        authorizationEndpoint.resetPassword(
            ResetPasswordParamsDto(
                phone = phone,
                recoveryHash = recoveryHash,
                password = password
            )
        )
    }

    override fun getLastLogin(): String =
        authorizationRepository.getLastLogin()

    override fun getCurrentCountry(): String =
        authorizationRepository.getCurrentCountry()

    override fun saveCurrentCountry(country: String) {
        authorizationRepository.putCurrentCountry(country)
    }

    private fun processPhone(phone: String): String =
        phone.replace("[-,. )(]".toRegex(), "")

}