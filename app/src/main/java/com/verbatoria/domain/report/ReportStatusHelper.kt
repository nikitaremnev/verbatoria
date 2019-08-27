package com.verbatoria.domain.report

import com.remnev.verbatoria.R

/**
 * @author nikitaremnev
 */

object ReportStatusHelper {

    fun getStatusLogoAndStringByEnum(reportStatus: ReportStatus): Pair<Int, Int> =
        when (reportStatus) {
            ReportStatus.NEW -> Pair(R.drawable.ic_report_new_large, R.string.report_status_new)
            ReportStatus.CANCELED -> Pair(R.drawable.ic_report_cancelled, R.string.report_status_cancelled)
            ReportStatus.UPLOADED -> Pair(R.drawable.ic_report_uploaded_large, R.string.report_status_uploaded)
            ReportStatus.READY -> Pair(R.drawable.ic_report_ready_large, R.string.report_status_ready)
            ReportStatus.SENT -> Pair(R.drawable.ic_report_sent_large, R.string.report_status_sent)
        }

}