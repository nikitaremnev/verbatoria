package com.verbatoria.ui.event.item

import com.remnev.verbatoria.R
import com.verbatoria.business.event.models.item.EventDetailReportItem
import com.verbatoria.domain.report.ReportStatus
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class EventDetailReportItemBinder: ViewBinder<EventDetailReportItemViewHolder, EventDetailReportItem>() {

    override fun bind(view: EventDetailReportItemViewHolder, data: EventDetailReportItem, position: Int) {
        view.setReportId(data.reportId)
        when (data.reportStatus) {
            ReportStatus.NEW -> view.setReportStatus(
                R.drawable.ic_report_new,
                R.string.report_status_new
            )
            ReportStatus.CANCELLED -> view.setReportStatus(
                R.drawable.ic_report_cancelled,
                R.string.report_status_cancelled
            )
            ReportStatus.UPLOADED -> view.setReportStatus(
                R.drawable.ic_report_uploaded,
                R.string.report_status_uploaded
            )
            ReportStatus.READY -> view.setReportStatus(
                R.drawable.ic_report_ready,
                R.string.report_status_ready
            )
            ReportStatus.SENT -> view.setReportStatus(
                R.drawable.ic_report_sent,
                R.string.report_status_sent
            )
        }
    }

}