package com.verbatoria.ui.event.item

import android.util.Patterns
import com.verbatoria.business.event.models.item.EventDetailClientItem
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class EventDetailClientItemBinder: ViewBinder<EventDetailClientItemViewHolder, EventDetailClientItem>() {

    override fun bind(view: EventDetailClientItemViewHolder, data: EventDetailClientItem, position: Int) {
        if (data.isLoading) {
            view.showLoading()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(data.email).matches()) {
            view.hideLoading()
            view.setEmailRequiredHint()
        } else if (data.name != null && data.phone != null) {
            view.hideLoading()
            view.setName(data.name ?: "")
            view.setPhone(data.phone ?: "")
        } else {
            view.hideLoading()
            view.showHint()
        }
    }

}