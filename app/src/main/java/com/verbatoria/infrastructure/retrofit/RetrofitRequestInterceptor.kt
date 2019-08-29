package com.verbatoria.infrastructure.retrofit

import com.verbatoria.domain.authorization.model.AuthorizationContext
import com.verbatoria.domain.authorization.model.OnlineAuthorization
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author n.remnev
 */

private const val TOKEN_HEADER = "token"

class RetrofitRequestInterceptor(
    private val authorizationContext: AuthorizationContext
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val authorization = authorizationContext.getAuthorization()
        val original = chain.request()
        val requestBuilder = original.newBuilder()
        if (authorization is OnlineAuthorization) {
            requestBuilder.addHeader(TOKEN_HEADER, authorization.token)
        }
        val request = requestBuilder.build()
        val response = chain.proceed(request)
        authorizationContext.invalidate()
        return response

    }

}