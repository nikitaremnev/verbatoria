package com.verbatoria.infrastructure.retrofit.endpoints.schedule.model.response

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class ScheduleItemResponseDto(
    val id: String,
    @SerializedName("from_time")
    val fromTime: String,
    @SerializedName("to_time")
    val toTime: String
)