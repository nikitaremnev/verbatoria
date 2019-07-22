package com.verbatoria.business.dashboard.calendar.models

import java.util.*

/**
 * @author n.remnev
 */

class EventItemModel(
    val clientName: String,
    val clientBirhtday: Date,
    val reportId: String,
    val status: String,
    val startDate: Date,
    val endDate: Date
) : CalendarItemModel