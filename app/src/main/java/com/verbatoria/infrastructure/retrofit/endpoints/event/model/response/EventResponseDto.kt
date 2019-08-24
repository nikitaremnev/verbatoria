package com.verbatoria.infrastructure.retrofit.endpoints.event.model.response

import com.google.gson.annotations.SerializedName
import com.verbatoria.infrastructure.retrofit.endpoints.child.model.response.ChildResponseDto
import com.verbatoria.infrastructure.retrofit.endpoints.dashboard.model.ReportResponseDto

/**
 * @author n.remnev
 */

data class EventResponseDto(
    val id: String,
    @SerializedName("start_at")
    val startAt: String,
    @SerializedName("end_at")
    val endAt: String,
    @SerializedName("instant_report")
    val isInstantReport: Boolean,
    @SerializedName("archimed")
    val isArchimedesIncluded: Boolean,
    @SerializedName("hobby")
    val isHobbyIncluded: Boolean,
    @SerializedName("is_archimed_allowed")
    val isArchimedesAllowed: Boolean,
    val child: ChildResponseDto,
    val report: ReportResponseDto
)