package com.verbatoria.infrastructure.retrofit.endpoints.event.model.params

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class EventParamsDto(
    @SerializedName("child_id")
    val childId: String,
    @SerializedName("location_id")
    val locationId: String,
    @SerializedName("start_at")
    val startAt: String,
    @SerializedName("end_at")
    val endAt: String,
    @SerializedName("instant_report")
    val isInstantReport: Boolean,
    @SerializedName("archimed")
    val isArchimedesIncluded: Boolean,
    @SerializedName("hobby")
    val isHobbyIncluded: Boolean
)