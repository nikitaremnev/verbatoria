package com.verbatoria.infrastructure.retrofit.endpoints.schedule.model.params

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class ClearScheduleParamsDto(
    @SerializedName("from_time")
    val fromTime: String,
    @SerializedName("to_time")
    val toTime: String
)