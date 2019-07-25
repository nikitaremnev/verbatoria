package com.verbatoria.infrastructure.retrofit.endpoints.client.model.params

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class ClientParamsDto(
    val name: String,
    val email: String,
    val phone: String,
    @SerializedName("phone_country")
    val phoneCountry: String
)