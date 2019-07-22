package com.verbatoria.infrastructure.retrofit.endpoints.dashboard.model

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class ClientResponseDto(
    val id: String,
    @SerializedName("client_id")
    val clientId: String,
    val name: String,
    @SerializedName("birth_day")
    val birthday: String,
    val gender: String
)