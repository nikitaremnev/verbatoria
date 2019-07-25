package com.verbatoria.ui.event.item

import com.verbatoria.business.event.models.item.EventDetailDateItem
import com.verbatoria.infrastructure.extensions.formatToDateMonthAndTime
import com.verbatoria.infrastructure.extensions.formatToTime
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class EventDetailDateItemBinder: ViewBinder<EventDetailDateItemViewHolder, EventDetailDateItem>() {

    override fun bind(view: EventDetailDateItemViewHolder, data: EventDetailDateItem, position: Int) {
        view.setDate(data.startDate.formatToDateMonthAndTime() + " - "  + data.endDate.formatToTime())
    }

}