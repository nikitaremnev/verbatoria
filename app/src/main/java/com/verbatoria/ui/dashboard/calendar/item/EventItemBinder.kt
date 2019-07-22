package com.verbatoria.ui.dashboard.calendar.item

import com.remnev.verbatoria.R
import com.verbatoria.business.dashboard.calendar.models.EventItemModel
import com.verbatoria.infrastructure.date.formatWithMillisecondsAndZeroOffset
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class EventItemBinder: ViewBinder<EventItemViewHolder, EventItemModel>() {

    override fun bind(view: EventItemViewHolder, data: EventItemModel, position: Int) {
        view.setStatusLogo(R.drawable.ic_report)
        view.setStatus(data.status)
        view.setClient(data.clientName)
        view.setReportId(data.reportId)
        view.setPeriod(data.startDate.formatWithMillisecondsAndZeroOffset())
    }

}