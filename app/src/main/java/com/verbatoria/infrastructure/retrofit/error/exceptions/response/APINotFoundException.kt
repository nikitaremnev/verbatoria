package com.verbatoria.infrastructure.retrofit.error.exceptions.response

/**
 * @author n.remnev
 */

class APINotFoundException(status: Int, description: String) :
    APIResponseException(status, description)
