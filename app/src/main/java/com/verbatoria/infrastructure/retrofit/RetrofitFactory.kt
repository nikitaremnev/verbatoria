package com.verbatoria.infrastructure.retrofit

import com.verbatoria.domain.authorization.model.AuthorizationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * @author n.remnev
 */

class RetrofitFactory(
    private val authorizationContext: AuthorizationContext?
) {

    fun createRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RetrofitCallAdapterFactory(authorizationContext))
            .client(createOkHttpClient())
            .build()

    private fun createOkHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()

        authorizationContext?.let { provider ->
            okHttpBuilder.addInterceptor(
                RetrofitRequestInterceptor(
                    provider
                )
            )
        }

        return okHttpBuilder
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .connectTimeout(APIConfig.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(APIConfig.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(APIConfig.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .followSslRedirects(false)
            .hostnameVerifier { _, _ -> true }
            .build()
    }

}