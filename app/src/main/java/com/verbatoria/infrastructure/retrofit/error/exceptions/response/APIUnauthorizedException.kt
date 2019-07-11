package com.verbatoria.infrastructure.retrofit.error.exceptions.response

/**
 * @author n.remnev
 */

class APIUnauthorizedException(status: Int, message: String) :
    APIWrongRequestException(status, message)