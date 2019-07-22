package com.verbatoria.ui.dashboard.calendar.item

import com.verbatoria.business.dashboard.calendar.models.EventItemModel
import com.verbatoria.business.report.ReportStatusHelper
import com.verbatoria.infrastructure.date.formatToTime
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class EventItemBinder: ViewBinder<EventItemViewHolder, EventItemModel>() {

    override fun bind(view: EventItemViewHolder, data: EventItemModel, position: Int) {
        val statusLogoAndText = ReportStatusHelper.getStatusLogoAndStringByEnum(data.status)
        view.setStatusLogoFromResourceId(statusLogoAndText.first)
        view.setStatusFromResourceId(statusLogoAndText.second)
        view.setClient(data.clientName)
        view.setReportId(data.reportId)
        view.setPeriod(data.startDate.formatToTime() + " - " + data.endDate.formatToTime())
    }

}