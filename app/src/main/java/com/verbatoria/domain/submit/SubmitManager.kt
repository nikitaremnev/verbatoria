package com.verbatoria.domain.submit

import com.verbatoria.infrastructure.retrofit.endpoints.report.ReportEndpoint

/**
 * @author n.remnev
 */

interface SubmitManager {


}

class SubmitManagerImpl(
    private val submitEndpoint: ReportEndpoint
) : SubmitManager {



}