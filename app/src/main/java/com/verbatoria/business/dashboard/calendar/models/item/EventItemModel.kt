package com.verbatoria.business.dashboard.calendar.models.item

import com.verbatoria.domain.report.ReportStatus
import java.util.*

/**
 * @author n.remnev
 */

class EventItemModel(
    val clientName: String,
    val clientAge: Int,
    val reportId: String,
    val status: ReportStatus,
    val startDate: Date,
    val endDate: Date
)