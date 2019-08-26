package com.verbatoria.business.event.models.item

import com.verbatoria.domain.report.ReportStatus
import com.verbatoria.ui.event.EventDetailMode

/**
 * @author n.remnev
 */

class EventDetailReportItem(
    mode: EventDetailMode,
    val reportId: String,
    val reportStatus: ReportStatus
) : EventDetailItem(mode)