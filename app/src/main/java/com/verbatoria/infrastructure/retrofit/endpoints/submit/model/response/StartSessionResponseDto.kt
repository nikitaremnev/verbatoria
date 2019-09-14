package com.verbatoria.infrastructure.retrofit.endpoints.submit.model.response

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class StartSessionResponseDto(
    val id: String,
    @SerializedName("child_id")
    val childId: String,
    val status: String,
    @SerializedName("verbatolog_id")
    val verbatologId: String,
    @SerializedName("location_id")
    val locationId: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)