package com.verbatoria.infrastructure.retrofit.error

/**
 * @author n.remnev
 */

class APIError(
    private val description: String?,
    private val message: String?,
    private val msg: String
) {

    fun getDescription(): String =
        description ?: (message ?: msg)

}
