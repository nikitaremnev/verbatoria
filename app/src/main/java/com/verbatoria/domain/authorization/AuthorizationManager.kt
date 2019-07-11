package com.verbatoria.domain.authorization

import com.verbatoria.infrastructure.retrofit.endpoints.authorization.AuthorizationEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.params.LoginParamsDto

/**
 * @author n.remnev
 */

interface AuthorizationManager {

    fun login(phone: String, password: String): Boolean

}

class AuthorizationManagerImpl(
    private val authorizationEndpoint: AuthorizationEndpoint,
    private val authorizationRepository: AuthorizationRepository
) : AuthorizationManager {

    override fun login(phone: String, password: String): Boolean {
        val response = authorizationEndpoint.login(
            LoginParamsDto(
                phone = phone,
                password = password
            )
        )
        return true
    }



}