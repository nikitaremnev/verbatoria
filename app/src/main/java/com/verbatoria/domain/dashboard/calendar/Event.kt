package com.verbatoria.domain.dashboard.calendar

import android.os.Parcelable
import com.verbatoria.domain.child.Child
import com.verbatoria.domain.report.Report
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author n.remnev
 */

@Parcelize
data class Event(
    val id: String,
    val clientId: String,
    var startDate: Date,
    var endDate: Date,
    var isInstantReport: Boolean,
    var isHobbyIncluded: Boolean,
    var isArchimedesIncluded: Boolean,
    var isArchimedesAllowed: Boolean,
    val child: Child,
    var report: Report
): Parcelable