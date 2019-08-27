package com.verbatoria.ui.event.item

import com.verbatoria.business.event.models.item.EventDetailReportItem
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class EventDetailReportItemBinder: ViewBinder<EventDetailReportItemViewHolder, EventDetailReportItem>() {

    override fun bind(view: EventDetailReportItemViewHolder, data: EventDetailReportItem, position: Int) {
        view.setReportId(data.reportId)
        view.setReportStatus(data.reportStatus)
    }

}