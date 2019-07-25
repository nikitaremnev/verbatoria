package com.verbatoria.business.event.models.item

import com.verbatoria.ui.event.EventDetailMode

/**
 * @author n.remnev
 */

class EventDetailChildItem(
    mode: EventDetailMode,
    val name: String,
    val age: Int
) : EventDetailItem(mode)