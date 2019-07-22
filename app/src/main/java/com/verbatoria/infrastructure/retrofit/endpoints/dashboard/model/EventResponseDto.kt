package com.verbatoria.infrastructure.retrofit.endpoints.dashboard.model

import com.google.gson.annotations.SerializedName

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
    val isArchimedIncluded: Boolean,
    @SerializedName("hobby")
    val isHobbyIncluded: Boolean,
    @SerializedName("is_archimed_allowed")
    val isArchimedAllowed: Boolean,
    val child: ClientResponseDto,
    val report: ReportResponseDto
)