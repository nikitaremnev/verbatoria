package com.verbatoria.infrastructure.retrofit.endpoints.schedule.model.response

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class ScheduleResponseDto(
    @SerializedName("data")
    val scheduleItems: List<ScheduleItemResponseDto>
)