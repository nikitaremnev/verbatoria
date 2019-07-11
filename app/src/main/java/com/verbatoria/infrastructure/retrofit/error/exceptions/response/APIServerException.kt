package com.verbatoria.infrastructure.retrofit.error.exceptions.response

/**
 * @author n.remnev
 */

class APIServerException(status: Int, message: String) : APIResponseException(status, message)
