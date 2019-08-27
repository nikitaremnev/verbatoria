package com.verbatoria.ui.event.item

import com.remnev.verbatoria.R
import com.verbatoria.business.event.models.item.EventDetailArchimedesItem
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class EventDetailArchimedesItemBinder: ViewBinder<EventDetailArchimedesItemViewHolder, EventDetailArchimedesItem>() {

    override fun bind(view: EventDetailArchimedesItemViewHolder, data: EventDetailArchimedesItem, position: Int) {
        if (data.isArchimedesIncluded) {
            view.setArchimedesState(R.string.event_detail_archimedes_included)
        } else {
            view.setArchimedesState(R.string.event_detail_archimedes_not_included)
            view.showArchimedesNotAllowedForVerbatolog()
        }
    }

}