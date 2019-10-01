package com.verbatoria.ui.dashboard.calendar.item

import com.verbatoria.business.dashboard.calendar.models.item.EventItemModel
import com.verbatoria.domain.child.model.Child
import com.verbatoria.domain.report.ReportStatusHelper
import com.verbatoria.domain.report.model.ReportStatus
import com.verbatoria.infrastructure.extensions.formatToTime
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class EventItemBinder : ViewBinder<EventItemViewHolder, EventItemModel>() {

    override fun bind(view: EventItemViewHolder, data: EventItemModel, position: Int) {
        val statusLogoAndText = ReportStatusHelper.getStatusLogoAndStringByEnum(data.status)
        view.setStatusLogoFromResourceId(statusLogoAndText.first)
        view.setStatusFromResourceId(statusLogoAndText.second)
        if (data.clientName == Child.NAME_NOT_SELECTED || data.clientAge == Child.AGE_NOT_SELECTED) {
            view.setChildNameAndAgeEmpty()
        } else {
            view.setChildNameAndAge(data.clientName, data.clientAge)
        }
        view.setReportId(data.reportId)
        view.setPeriod(data.startDate.formatToTime() + " - " + data.endDate.formatToTime())
        if (data.status == ReportStatus.SENT) {
            view.setCompletedStatus()
        } else {
            view.setNotCompletedStatus()
        }
    }

}