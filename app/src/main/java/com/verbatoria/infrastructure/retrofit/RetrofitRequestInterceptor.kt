package com.verbatoria.infrastructure.retrofit

import com.verbatoria.domain.authorization.AuthorizationContext
import com.verbatoria.domain.authorization.model.OnlineAuthorization
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author n.remnev
 */

private const val COOKIE_HEADER = "Cookie"
private const val FLAGS = "; httponly; secure"

class RetrofitRequestInterceptor(
    private val authorizationContext: AuthorizationContext
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val authorization = authorizationContext.getAuthorization()
        val original = chain.request()
        val requestBuilder = original.newBuilder()
        if (authorization is OnlineAuthorization) {
            requestBuilder.addHeader(COOKIE_HEADER, authorization.cookieToken + FLAGS)
        }
        val request = requestBuilder.build()
        val response = chain.proceed(request)
        authorizationContext.invalidate()
        return response

    }

}