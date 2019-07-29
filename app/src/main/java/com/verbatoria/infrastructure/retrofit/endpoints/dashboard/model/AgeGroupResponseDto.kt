package com.verbatoria.infrastructure.retrofit.endpoints.dashboard.model

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class AgeGroupResponseDto(
    @SerializedName("min_age")
    val minAge: Int,
    @SerializedName("max_age")
    val maxAge: Int,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("is_archimed_allowed")
    val isArchimedesAllowed: Boolean
)