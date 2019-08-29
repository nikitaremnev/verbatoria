package com.verbatoria.business.dashboard.calendar.models

import com.verbatoria.domain.report.model.ReportStatus
import java.util.*

/**
 * @author n.remnev
 */

data class EventModel(
    val eventId: String,
    val startAt: Date,
    val endAt: Date,
    val status: ReportStatus,
    val startDate: Date,
    val endDate: Date
)

//val id: String,
//@SerializedName("start_at")
//val startAt: String,
//@SerializedName("end_at")
//val endAt: String,
//@SerializedName("instant_report")
//val isInstantReport: Boolean,
//@SerializedName("archimed")
//val isArchimedIncluded: Boolean,
//@SerializedName("hobby")
//val isHobbyIncluded: Boolean,
//@SerializedName("is_archimed_allowed")
//val isArchimedAllowed: Boolean,
//val child: ClientResponseDto,
//
//val id: String,
//@SerializedName("client_id")
//val clientId: String,
//val name: String,
//@SerializedName("birth_day")
//val birthday: String,
//val gender: String
//
//
//val report: ReportResponseDto
//
//val id: String,
//@SerializedName("child_id")
//val childId: String,
//@SerializedName("location_id")
//val locationId: String,
//@SerializedName("verbatolog_id")
//val verbatologId: String,
//@SerializedName("report_id")
//val reportId: String,
//@SerializedName("status")
//val status: String,
//@SerializedName("created_at")
//val createdAt: String,
//@SerializedName("updated_at")
//val updatedAt: String
