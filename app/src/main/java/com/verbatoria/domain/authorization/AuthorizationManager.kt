package com.verbatoria.domain.authorization

import com.verbatoria.infrastructure.retrofit.endpoints.authorization.AuthorizationEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.params.LoginParamsDto

/**
 * @author n.remnev
 */

interface AuthorizationManager {

    fun login(phone: String, password: String): Boolean

    fun getLastLogin(): String

    fun getCurrentCountry(): String

    fun saveCurrentCountry(country: String)

}

class AuthorizationManagerImpl(
    private val authorizationEndpoint: AuthorizationEndpoint,
    private val authorizationRepository: AuthorizationRepository
) : AuthorizationManager {

    override fun login(phone: String, password: String): Boolean {
        val response = authorizationEndpoint.login(
            LoginParamsDto(
                phone = processPhone(phone),
                password = password
            )
        )
        return true
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