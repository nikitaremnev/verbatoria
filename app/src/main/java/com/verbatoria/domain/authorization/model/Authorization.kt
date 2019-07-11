package com.verbatoria.domain.authorization.model

import java.io.Serializable

/**
 * @author n.remnev
 */

interface Authorization : Serializable {

    val token: String?

    val cookieToken: String?

    val isAuthenticated: Boolean

}
