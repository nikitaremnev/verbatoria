package com.verbatoria.infrastructure.retrofit.endpoints.authorization

import com.verbatoria.infrastructure.retrofit.APIConstants
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.response.SMSLoginResponseDto
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author n.remnev
 */

private const val VERBATORIA_SMS_TOKEN = "eb7c35c2-32c5-414c-bf6c-89470be41ca1"

interface SMSLoginEndpoint {

    @POST(APIConstants.SMS_CONFIRMATION_CODE_URL)
    fun sendSmsLogin(
        @Query("phone") phone: String,
        @Query("text") smsText: String,
        @Query("token") token: String = VERBATORIA_SMS_TOKEN
    ): SMSLoginResponseDto

}