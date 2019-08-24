package com.verbatoria.domain.report

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author n.remnev
 */

@Parcelize
data class Report(
    val id: String,
    val childId: String,
    val locationId: String,
    val reportId: String,
    val status: ReportStatus,
    val createdAt: Date,
    val updatedAt: Date
): Parcelable


