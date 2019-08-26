package com.verbatoria.domain.report

import com.verbatoria.infrastructure.retrofit.endpoints.report.ReportEndpoint

/**
 * @author n.remnev
 */

interface ReportManager {

    fun sendReportToLocation(reportId: String)

}

class ReportManagerImpl(
    private val reportEndpoint: ReportEndpoint
) : ReportManager {

    override fun sendReportToLocation(reportId: String) {
        reportEndpoint.sendReportToLocation(reportId)
    }

}