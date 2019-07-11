package com.verbatoria.infrastructure.retrofit.error

import com.verbatoria.infrastructure.retrofit.error.HttpStatusChecker.isClientError
import com.verbatoria.infrastructure.retrofit.error.HttpStatusChecker.isNotFoundError
import com.verbatoria.infrastructure.retrofit.error.HttpStatusChecker.isRedirect
import com.verbatoria.infrastructure.retrofit.error.HttpStatusChecker.isServerError
import com.verbatoria.infrastructure.retrofit.error.HttpStatusChecker.isUnauthorized
import com.verbatoria.infrastructure.retrofit.error.exceptions.response.*

/**
 * @author n.remnev
 */

private const val AUTHENTICATION_FAILED = "Authentication Failed"

class APIResponseErrorHandler {

    fun handleError(status: Int, message: String): APIResponseException =
        if (isRedirect(status)) {
            APIRedirectException(
                status,
                message
            )
        } else if (isClientError(status)) {
            if (isUnauthorized(status) && AUTHENTICATION_FAILED != message) {
                APIUnauthorizedException(
                    status,
                    message
                )
            } else if (isNotFoundError(status)) {
                APINotFoundException(
                    status,
                    message
                )
            } else {
                APIWrongRequestException(
                    status,
                    message
                )
            }
        } else if (isServerError(status)) {
            APIServerException(
                status,
                message
            )
        } else {
            APIResponseException(
                status,
                message
            )
        }

}
