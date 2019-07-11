package com.verbatoria.domain.authorization.model

/**
 * @author n.remnev
 */

class OfflineAuthorization : Authorization {

    override val token: String?
        get() = null

    override val cookieToken: String?
        get() = null

    override val isAuthenticated: Boolean
        get() = false

}

