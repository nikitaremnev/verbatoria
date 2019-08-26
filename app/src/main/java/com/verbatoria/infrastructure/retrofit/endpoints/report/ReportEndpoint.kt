package com.verbatoria.infrastructure.retrofit.endpoints.report

import com.verbatoria.infrastructure.retrofit.APIConstants
import com.verbatoria.infrastructure.retrofit.APIConstants.REPORT_ID_PATH_KEY
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * @author n.remnev
 */

interface ReportEndpoint {

    @POST(APIConstants.SEND_REPORT_TO_LOCATION_URL)
    fun sendReportToLocation(
        @Path(value = REPORT_ID_PATH_KEY) reportId: String
    ): ResponseBody

}