package com.verbatoria.business.event.models.item

import com.verbatoria.ui.event.EventDetailMode

/**
 * @author n.remnev
 */

class EventDetailArchimedesItem(
    mode: EventDetailMode,
    var isArchimedesIncluded: Boolean
) : EventDetailItem(mode)