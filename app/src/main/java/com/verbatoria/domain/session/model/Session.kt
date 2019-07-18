package com.verbatoria.domain.session.model

import com.verbatoria.domain.authorization.model.Authorization
import com.verbatoria.domain.authorization.model.OfflineAuthorization

/**
 * @author n.remnev
 */

class Session(
    val authorization: Authorization,
    var isSessionRemoved: Boolean = false
) {

    fun isOffline(): Boolean =
        authorization is OfflineAuthorization

    fun markSessionAsRemoved() {
        isSessionRemoved = true
    }

}