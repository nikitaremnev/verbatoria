package com.verbatoria.domain.session.model

import com.verbatoria.domain.authorization.model.Authorization
import com.verbatoria.domain.authorization.model.OfflineAuthorization
import java.util.*

/**
 * @author n.remnev
 */

class Session(
    val authorization: Authorization,
    val lifetime: Long,
    val login: String,
    val updateDateTime: Long,
    var isSessionRemoved: Boolean = false
) {

    fun isOffline(): Boolean =
        authorization is OfflineAuthorization

    fun markSessionAsRemoved() {
        isSessionRemoved = true
    }

    fun getUpdateDateTime(): Date? =
        if (updateDateTime == 0L) {
            null
        } else {
            Date(updateDateTime)
        }

}