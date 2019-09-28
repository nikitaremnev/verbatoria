package com.verbatoria.domain.report

import com.remnev.verbatoria.R
import com.verbatoria.domain.report.model.ReportStatus

/**
 * @author nikitaremnev
 */

object ReportStatusHelper {

    fun getStatusLogoAndStringByEnum(reportStatus: ReportStatus): Pair<Int, Int> =
        when (reportStatus) {
            ReportStatus.NEW -> Pair(R.drawable.ic_report_new, R.string.report_status_new)
            ReportStatus.CANCELED -> Pair(R.drawable.ic_report_cancelled, R.string.report_status_cancelled)
            ReportStatus.UPLOADED -> Pair(R.drawable.ic_report_uploaded, R.string.report_status_uploaded)
            ReportStatus.READY -> Pair(R.drawable.ic_report_uploaded, R.string.report_status_uploaded)
            ReportStatus.SENT -> Pair(R.drawable.ic_report_sent, R.string.report_status_sent)
        }

}