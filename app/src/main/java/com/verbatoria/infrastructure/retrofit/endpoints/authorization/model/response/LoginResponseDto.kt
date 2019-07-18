package com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.response

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class LoginResponseDto(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_at")
    val expiresToken: String,
    val status: String,
    @SerializedName("location_id")
    val locationId: String,
    val error: String?
)