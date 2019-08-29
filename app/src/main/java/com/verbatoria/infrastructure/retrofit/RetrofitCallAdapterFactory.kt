package com.verbatoria.infrastructure.retrofit

import com.verbatoria.domain.authorization.model.AuthorizationContext
import com.verbatoria.infrastructure.retrofit.error.APIResponseErrorHandler
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Response

/**
 * @author n.remnev
 */

class RetrofitCallAdapterFactory(
    private val authorizationProvider: AuthorizationContext?
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<Any, Any>? {
        if (getRawType(returnType) === Call::class.java) return null
        if (getRawType(returnType) !== Response::class.java) {
            return RetrofitCallAdapter(retrofit, authorizationProvider,
                APIResponseErrorHandler(), returnType)
        }
        if (returnType !is ParameterizedType) {
            throw IllegalStateException("Response must be parameterized as Response<Foo> or Response<? extends Foo>")
        }
        val responseType = getParameterUpperBound(0, returnType)
        return RetrofitCallAdapter(retrofit, authorizationProvider,
            APIResponseErrorHandler(), responseType)
    }

}