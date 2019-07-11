package com.verbatoria.domain.authorization.model

/**
 * @author n.remnev
 */

private const val JSESSION_COOKIE = "JSESSIONID="

class OnlineAuthorization(
    override val token: String
) : Authorization {

    override val cookieToken: String
        get() = JSESSION_COOKIE + token

    override val isAuthenticated: Boolean
        get() = true

}

