package com.verbatoria.business.event.models.item

import com.verbatoria.ui.event.EventDetailMode

/**
 * @author n.remnev
 */

class EventDetailClientItem(
    mode: EventDetailMode,
    var name: String? = null,
    var phone: String? = null
) : EventDetailItem(mode)