package com.verbatoria.infrastructure.retrofit.endpoints.authorization

import com.verbatoria.infrastructure.retrofit.APIConstants
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.params.LoginParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.params.RecoveryPasswordParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.params.ResetPasswordParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.response.LoginResponseDto
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.model.response.MessageResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author n.remnev
 */

interface AuthorizationEndpoint {

    @POST(APIConstants.LOGIN_URL)
    fun login(@Body params: LoginParamsDto): LoginResponseDto

    @POST(APIConstants.RECOVERY_PASSWORD_URL)
    fun recoveryPassword(@Body params: RecoveryPasswordParamsDto): MessageResponseDto

    @POST(APIConstants.RESET_PASSWORD_URL)
    fun resetPassword(@Body params: ResetPasswordParamsDto): MessageResponseDto

}