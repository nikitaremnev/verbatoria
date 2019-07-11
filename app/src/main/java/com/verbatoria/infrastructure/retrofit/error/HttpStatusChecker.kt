package com.verbatoria.infrastructure.retrofit.error

/**
 * @author n.remnev
 */

object HttpStatusChecker {

    fun isRedirect(status: Int): Boolean =
        status in 300..399

    fun isClientError(status: Int): Boolean =
        status in 400..499

    fun isNotFoundError(status: Int): Boolean =
        status == 404

    fun isServerError(status: Int): Boolean =
        status >= 500

    fun isUnauthorized(status: Int): Boolean =
        status == 401 || status == 403

}
