package com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.response

/**
 * @author n.remnev
 */

data class MessageResponseDto(
    val message: String,
    val error: String?
)