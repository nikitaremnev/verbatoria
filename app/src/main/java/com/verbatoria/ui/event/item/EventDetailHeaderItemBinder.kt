package com.verbatoria.ui.event.item

import com.verbatoria.business.event.models.item.EventDetailHeaderItem
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class EventDetailHeaderItemBinder: ViewBinder<EventDetailHeaderItemViewHolder, EventDetailHeaderItem>() {

    override fun bind(view: EventDetailHeaderItemViewHolder, data: EventDetailHeaderItem, position: Int) {
        view.setHeader(data.headerStringResource)
    }

}