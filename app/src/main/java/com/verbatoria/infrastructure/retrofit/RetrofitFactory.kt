package com.verbatoria.infrastructure.retrofit

import com.verbatoria.domain.authorization.model.AuthorizationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


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

        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )

        val sslContext = SSLContext.getInstance("SSL").apply {
            init(null, trustAllCerts, SecureRandom())
        }

        return okHttpBuilder
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .connectTimeout(APIConfig.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(APIConfig.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(APIConfig.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .followSslRedirects(false)
            .hostnameVerifier { _, _ -> true }
            .build()
    }

}