package com.verbatoria.business.event.models.item

import com.verbatoria.ui.event.EventDetailMode

/**
 * @author n.remnev
 */

class EventDetailChildItem(
    mode: EventDetailMode,
    val name: String? = null,
    val age: Int? = null
) : EventDetailItem(mode)