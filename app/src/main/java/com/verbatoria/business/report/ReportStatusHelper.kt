package com.verbatoria.business.report

import com.remnev.verbatoria.R

/**
 * @author nikitaremnev
 */

object ReportStatusHelper {

    fun getStatusLogoAndStringByEnum(reportStatus: ReportStatus): Pair<Int, Int> =
        when (reportStatus) {
            ReportStatus.NEW -> Pair(R.drawable.ic_report_new_large, R.string.calendar_report_new)
            ReportStatus.CANCELLED -> Pair(R.drawable.ic_report_cancelled, R.string.calendar_report_cancelled)
            ReportStatus.UPLOADED -> Pair(R.drawable.ic_report_uploaded_large, R.string.calendar_report_uploaded)
            ReportStatus.READY -> Pair(R.drawable.ic_report_ready_large, R.string.calendar_report_ready)
            ReportStatus.SENT -> Pair(R.drawable.ic_report_sent_large, R.string.calendar_report_sent)
        }

}