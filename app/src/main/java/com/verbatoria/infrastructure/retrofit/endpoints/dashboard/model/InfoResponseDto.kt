package com.verbatoria.infrastructure.retrofit.endpoints.dashboard.model

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class InfoResponseDto(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("middle_name")
    val middleName: String,
    val phone: String,
    val email: String,
    @SerializedName("location_id")
    val locationId: String,
    @SerializedName("is_archimed_allowed")
    val isArchimedesAllowed: Boolean
)