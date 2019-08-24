package com.verbatoria.business.event.models.item

import com.verbatoria.ui.event.EventDetailMode

/**
 * @author n.remnev
 */

class EventDetailChildItem(
    mode: EventDetailMode,
    var name: String? = null,
    var age: Int? = null
) : EventDetailItem(mode)