package com.verbatoria.domain.report.model

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
): Parcelable {

    fun isNew() = status == ReportStatus.NEW

    fun isSent() = status == ReportStatus.SENT

    fun isSentOrReady() = status == ReportStatus.SENT || status == ReportStatus.READY

    fun isCanceled() = status == ReportStatus.CANCELED || status == ReportStatus.READY

}


