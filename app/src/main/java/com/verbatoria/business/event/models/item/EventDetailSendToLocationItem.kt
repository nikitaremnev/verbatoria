package com.verbatoria.business.event.models.item

import com.verbatoria.ui.event.EventDetailMode

/**
 * @author n.remnev
 */

class EventDetailSendToLocationItem(
    mode: EventDetailMode,
    var isLoading: Boolean = false,
    var isAlreadySent: Boolean = false
) : EventDetailItem(mode)