package com.verbatoria.business.event.models.item

import com.verbatoria.ui.event.EventDetailMode
import java.util.*

/**
 * @author n.remnev
 */

class EventDetailDateItem(
    mode: EventDetailMode,
    val startDate: Date,
    val endDate: Date
) : EventDetailItem(mode)