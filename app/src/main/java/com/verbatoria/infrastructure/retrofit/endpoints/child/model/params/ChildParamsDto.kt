package com.verbatoria.infrastructure.retrofit.endpoints.child.model.params

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class ChildParamsDto(
    @SerializedName("client_id")
    val clientId: String,
    val name: String,
    @SerializedName("birth_day")
    val birthday: String,
    @SerializedName("gender")
    val gender: String
)