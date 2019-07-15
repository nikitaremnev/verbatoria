package com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.response

/**
 * @author n.remnev
 */

data class SMSLoginResponseDto(
    val result: String,
    val guid: String,
    val code: String
)