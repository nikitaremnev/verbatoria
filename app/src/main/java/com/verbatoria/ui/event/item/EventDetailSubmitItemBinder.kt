package com.verbatoria.ui.event.item

import com.verbatoria.business.event.models.item.EventDetailSubmitItem
import com.verbatoria.ui.common.ViewBinder
import com.verbatoria.ui.event.EventDetailMode

/**
 * @author n.remnev
 */

class EventDetailSubmitItemBinder: ViewBinder<EventDetailSubmitItemViewHolder, EventDetailSubmitItem>() {

    override fun bind(view: EventDetailSubmitItemViewHolder, data: EventDetailSubmitItem, position: Int) {
        when (data.mode) {
            EventDetailMode.CREATE_NEW -> {
                view.setCreateNewEventTitle()
            }
            EventDetailMode.EDIT -> {
                view.setEditEventTitle()
            }
            EventDetailMode.START -> {
                view.setStartEventTitle()
            }
        }
        if (data.isAllFieldsFilled) {
            view.setSubmitButtonEnabled()
        } else {
            view.setSubmitButtonDisabled()
        }
    }

}