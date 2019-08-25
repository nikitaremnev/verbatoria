package com.verbatoria.business.event.models.item

import com.verbatoria.ui.event.EventDetailMode

/**
 * @author n.remnev
 */

class EventDetailSubmitItem(
    mode: EventDetailMode,
    var isAllFieldsFilled: Boolean = false
) : EventDetailItem(mode)