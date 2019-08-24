package com.verbatoria.ui.event.item

import com.verbatoria.business.event.models.item.EventDetailChildItem
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class EventDetailChildItemBinder: ViewBinder<EventDetailChildItemViewHolder, EventDetailChildItem>() {

    override fun bind(view: EventDetailChildItemViewHolder, data: EventDetailChildItem, position: Int) {
        if (data.name != null && data.age != null) {
            view.setName(data.name ?: "")
            view.setAge(data.age ?: 0)
        } else {
            view.showHint()
        }
    }

}