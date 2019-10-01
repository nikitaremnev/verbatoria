package com.verbatoria.infrastructure.retrofit

import com.verbatoria.domain.authorization.model.AuthorizationContext
import com.verbatoria.infrastructure.retrofit.error.APIError
import com.verbatoria.infrastructure.retrofit.error.APIResponseErrorHandler
import com.verbatoria.infrastructure.retrofit.error.HttpStatusChecker.isServerError
import com.verbatoria.infrastructure.retrofit.error.exceptions.response.APIUnauthorizedException
import okhttp3.MediaType
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.Type

/**
 * @author n.remnev
 */

private const val TEXT_MEDIA_TYPE = "text/plain"

class RetrofitCallAdapter<R>(
    private val retrofit: Retrofit,
    private val authorizationProvider: AuthorizationContext?,
    private val responseErrorHandler: APIResponseErrorHandler,
    private val returnType: Type
) : CallAdapter<R, Any> {

    override fun adapt(call: Call<R>): Any? =
        try {
            execute(call)
        } catch (e: Exception) {
            if (!call.isCanceled) {
                throw e
            } else {
                //empty
            }
        }

    override fun responseType(): Type =
        returnType

    private fun <R> execute(call: Call<R>): Any? =
        call.execute().let { response ->
            if (response.isSuccessful) {
                response.body()
            } else {
                throw responseErrorHandler.handleError(response.code(), readErrorDescription(response))
                    .also { error ->
                        if (error is APIUnauthorizedException) {
                            authorizationProvider?.unauthorize()
                        }
                    }
            }
        }

    private fun readErrorDescription(response: Response<*>): String =
        response.errorBody()?.let { errorBody ->
            val plainTextErrorType = isServerError(response.code())
                    && errorBody.contentType() == MediaType.parse(TEXT_MEDIA_TYPE)
            try {
                if (plainTextErrorType) {
                    errorBody.string()
                } else {
                    if (errorBody.contentType() == MediaType.parse(TEXT_MEDIA_TYPE)) {
                        errorBody.string()
                    } else {
                        retrofit.responseBodyConverter<APIError>(
                            APIError::class.java,
                            arrayOfNulls(0)
                        )
                            .convert(errorBody)?.error
                    }
                }
            } catch (exception: IOException) {
                if (plainTextErrorType) {
                    "Error while parsing description from body"
                } else {
                    "Error while parsing description from json"
                }
            }
        } ?: ""

}