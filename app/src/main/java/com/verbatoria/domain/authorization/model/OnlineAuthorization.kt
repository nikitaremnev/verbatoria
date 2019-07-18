package com.verbatoria.domain.authorization.model

/**
 * @author n.remnev
 */

private const val TOKEN_COOKIE = "token="

class OnlineAuthorization(
    override val token: String
) : Authorization {

    override val cookieToken: String
        get() = TOKEN_COOKIE + token

    override val isAuthenticated: Boolean
        get() = true

}

