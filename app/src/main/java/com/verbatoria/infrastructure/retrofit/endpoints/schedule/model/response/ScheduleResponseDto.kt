package com.verbatoria.infrastructure.retrofit.endpoints.schedule.model.response

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class ScheduleResponseDto(
    @SerializedName("total_entries")
    val totalEntries: Int,
    @SerializedName("current_page")
    val currentPage: Int?,
    @SerializedName("next_page")
    val nextPage: Int?,
    @SerializedName("previous_page")
    val previousPage: Int?,
    @SerializedName("data")
    val scheduleItems: List<ScheduleItemResponseDto>
)