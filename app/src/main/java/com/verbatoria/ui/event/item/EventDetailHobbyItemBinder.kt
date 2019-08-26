package com.verbatoria.ui.event.item

import com.remnev.verbatoria.R
import com.verbatoria.business.event.models.item.EventDetailHobbyItem
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class EventDetailHobbyItemBinder: ViewBinder<EventDetailHobbyItemViewHolder, EventDetailHobbyItem>() {

    override fun bind(view: EventDetailHobbyItemViewHolder, data: EventDetailHobbyItem, position: Int) {
        if (data.isHobbyIncluded) {
            view.setHobbyIncludedShortStatus(R.string.event_detail_hobby_panel_included)
            view.setHobbyIncludedFullStatus(R.string.event_detail_hobby_included_status)
        } else {
            view.setHobbyIncludedShortStatus(R.string.event_detail_hobby_panel_not_included)
            view.setHobbyIncludedFullStatus(R.string.event_detail_hobby_not_included_status)
        }
        if (data.isLoading) {
            view.showLoading()
        } else {
            view.hideLoading()
        }
    }

}