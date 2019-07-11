package com.verbatoria.infrastructure.retrofit.error.exceptions.response

/**
 * @author n.remnev
 */

open class APIWrongRequestException(status: Int, message: String) :
    APIResponseException(status, message)
