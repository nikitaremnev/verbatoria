package com.verbatoria.domain.authorization.model

import com.verbatoria.domain.authorization.model.Authorization

/**
 * @author n.remnev
 */

interface AuthorizationContext {

    fun getAuthorization(): Authorization

    fun invalidate()

    fun unauthorize()

}