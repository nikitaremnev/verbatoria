package com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.params

/**
 * @author n.remnev
 */

data class LoginParamsDto(
    val phone: String,
    val password: String
)