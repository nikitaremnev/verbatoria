package com.verbatoria.infrastructure.retrofit.endpoints.schedule.model.params

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class SaveScheduleParamsDto(
    @SerializedName("schedule_entries")
    val scheduleItems: List<ScheduleItemParamsDto>
)