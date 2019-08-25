package com.verbatoria.ui.event.item

import com.verbatoria.business.event.models.item.EventDetailTimeItem
import com.verbatoria.infrastructure.extensions.formatToDateMonthAndTime
import com.verbatoria.infrastructure.extensions.formatToTime
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class EventDetailTimeItemBinder: ViewBinder<EventDetailTimeItemViewHolder, EventDetailTimeItem>() {

    override fun bind(view: EventDetailTimeItemViewHolder, data: EventDetailTimeItem, position: Int) {
        if (data.startDate != null && data.endDate != null) {
            view.setTime(
                data.startDate?.formatToDateMonthAndTime() + " - "  + data.endDate?.formatToTime()
            )
        } else {
            view.showHint()
        }
    }

}