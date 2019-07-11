package com.verbatoria.infrastructure.retrofit.error.exceptions.response

import com.verbatoria.infrastructure.retrofit.error.exceptions.api.APIException

/**
 * @author n.remnev
 */

open class APIResponseException(val status: Int, description: String?) :
    APIException(description ?: "Unknown server error") {

    val description: String = description ?: "Unknown server error"

}