package com.verbatoria.business.event.models.item

import com.verbatoria.ui.event.EventDetailMode

/**
 * @author n.remnev
 */

class EventDetailHeaderItem(
    mode: EventDetailMode,
    val headerStringResource: Int
) : EventDetailItem(mode)