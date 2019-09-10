package com.verbatoria.infrastructure.retrofit.endpoints.submit

import com.verbatoria.data.network.request.StartSessionRequestModel
import com.verbatoria.data.network.response.FinishSessionResponseModel
import com.verbatoria.data.network.response.StartSessionResponseModel
import com.verbatoria.infrastructure.retrofit.APIConstants
import com.verbatoria.infrastructure.retrofit.APIConstants.SESSION_ID_PATH_KEY
import com.verbatoria.infrastructure.retrofit.endpoints.submit.model.params.StartSessionParamsDto
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * @author n.remnev
 */

interface SubmitEndpoint {

    @POST(APIConstants.START_SESSION_URL)
    fun startSession(
        @Body params: StartSessionParamsDto
    ): Observable<StartSessionResponseModel>

    @POST(APIConstants.ADD_RESULTS_TO_SESSION_URL)
    fun sendData(
        @Path(value = SESSION_ID_PATH_KEY) sessionId: String,
        @Body body: RequestBody
    ): Observable<ResponseBody>

    @POST(APIConstants.FINISH_SESSION_URL)
    fun finishSession(
        @Path(value = SESSION_ID_PATH_KEY) sessionId: String
    ): Observable<FinishSessionResponseModel>

}