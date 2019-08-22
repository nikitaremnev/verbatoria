package com.verbatoria.business.event.models.item

import com.verbatoria.ui.event.EventDetailMode

/**
 * @author n.remnev
 */

class EventDetailClientItem(
    mode: EventDetailMode,
    val name: String? = null,
    val phone: String? = null
) : EventDetailItem(mode)