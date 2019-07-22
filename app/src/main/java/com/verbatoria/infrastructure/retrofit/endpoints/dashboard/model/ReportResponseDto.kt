package com.verbatoria.infrastructure.retrofit.endpoints.dashboard.model

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class ReportResponseDto(
    val id: String,
    @SerializedName("child_id")
    val childId: String,
    @SerializedName("location_id")
    val locationId: String,
    @SerializedName("verbatolog_id")
    val verbatologId: String,
    @SerializedName("report_id")
    val reportId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)