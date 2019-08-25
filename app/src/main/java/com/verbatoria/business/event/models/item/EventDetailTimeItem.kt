package com.verbatoria.business.event.models.item

import com.verbatoria.ui.event.EventDetailMode
import java.util.*

/**
 * @author n.remnev
 */

class EventDetailTimeItem(
    mode: EventDetailMode,
    var startDate: Date? = null,
    var endDate: Date? = null
) : EventDetailItem(mode)