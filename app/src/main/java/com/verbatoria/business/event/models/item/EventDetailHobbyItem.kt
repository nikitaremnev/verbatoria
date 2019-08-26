package com.verbatoria.business.event.models.item

import com.verbatoria.ui.event.EventDetailMode

/**
 * @author n.remnev
 */

class EventDetailHobbyItem(
    mode: EventDetailMode,
    var isHobbyIncluded: Boolean = false,
    var isLoading: Boolean = false
) : EventDetailItem(mode)