package com.verbatoria.business.event.models.item

import com.verbatoria.ui.event.EventDetailMode

/**
 * @author n.remnev
 */

class EventDetailClientItem(
    mode: EventDetailMode,
    var isLoading: Boolean = true,
    var name: String? = null,
    var phone: String? = null,
    var email: String? = null
) : EventDetailItem(mode)