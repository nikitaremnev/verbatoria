package com.verbatoria.ui.event.item

import com.verbatoria.business.event.models.item.EventDetailSendToLocationItem
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class EventDetailSendToLocationItemBinder: ViewBinder<EventDetailSendToLocationItemViewHolder, EventDetailSendToLocationItem>() {

    override fun bind(view: EventDetailSendToLocationItemViewHolder, data: EventDetailSendToLocationItem, position: Int) {
        if (data.isLoading) {
            view.showLoading()
        } else {
            view.hideLoading()
        }
        if (data.isAlreadySent) {
            view.setAlreadySent()
        }
    }

}