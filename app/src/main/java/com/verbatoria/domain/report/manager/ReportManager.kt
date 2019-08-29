package com.verbatoria.domain.report.manager

import com.verbatoria.infrastructure.retrofit.endpoints.report.ReportEndpoint

/**
 * @author n.remnev
 */

interface ReportManager {

    fun sendReportToLocation(reportId: String)

    fun includeAttentionMemory(reportId: String)

}

class ReportManagerImpl(
    private val reportEndpoint: ReportEndpoint
) : ReportManager {

    override fun sendReportToLocation(reportId: String) {
        reportEndpoint.sendReportToLocation(reportId)
    }

    override fun includeAttentionMemory(reportId: String) {
        reportEndpoint.includeAttentionMemory(reportId)
    }

}