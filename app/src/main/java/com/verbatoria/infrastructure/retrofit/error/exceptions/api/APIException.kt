package com.verbatoria.infrastructure.retrofit.error.exceptions.api

/**
 * @author n.remnev
 */

open class APIException : RuntimeException {

    constructor(detailMessage: String) : super(detailMessage)

    constructor(throwable: Throwable) : super(throwable)

}
