package com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.response

/**
 * @author n.remnev
 */

data class LoginResponseDto(
    val accessToken: String,
    val expiresToken: String,
    val status: String,
    val locationId: String,
    val error: String?
)