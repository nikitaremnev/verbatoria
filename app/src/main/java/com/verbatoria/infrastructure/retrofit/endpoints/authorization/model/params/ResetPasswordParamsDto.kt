package com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.params

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class ResetPasswordParamsDto(
    val phone: String,
    @SerializedName("recovery_hash")
    val recoveryHash: String,
    val password: String
)