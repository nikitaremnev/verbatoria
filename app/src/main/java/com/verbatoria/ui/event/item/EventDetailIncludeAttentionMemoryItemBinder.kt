package com.verbatoria.ui.event.item

import com.verbatoria.business.event.models.item.EventDetailIncludeAttentionMemoryItem
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class EventDetailIncludeAttentionMemoryItemBinder: ViewBinder<EventDetailIncludeAttentionMemoryItemViewHolder, EventDetailIncludeAttentionMemoryItem>() {

    override fun bind(view: EventDetailIncludeAttentionMemoryItemViewHolder, data: EventDetailIncludeAttentionMemoryItem, position: Int) {
        if (data.isLoading) {
            view.showLoading()
        } else {
            view.hideLoading()
        }
        if (data.isAttentionMemoryIncluded) {
            view.setAttentionMemoryIncluded()
        }
    }

}