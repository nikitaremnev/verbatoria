package com.verbatoria.ui.event.item

import com.verbatoria.business.event.models.item.EventDetailClientItem
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class EventDetailClientItemBinder: ViewBinder<EventDetailClientItemViewHolder, EventDetailClientItem>() {

    override fun bind(view: EventDetailClientItemViewHolder, data: EventDetailClientItem, position: Int) {
        if (data.name != null && data.phone != null) {
            view.setName(data.name)
            view.setPhone(data.phone)
        } else {
            view.showHint()
        }
    }

}