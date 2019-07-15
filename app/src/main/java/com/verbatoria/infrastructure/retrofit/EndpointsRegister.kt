package com.verbatoria.infrastructure.retrofit

import com.verbatoria.infrastructure.retrofit.endpoints.authorization.AuthorizationEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.SMSLoginEndpoint
import retrofit2.Retrofit

/**
 * @author n.remnev
 */

interface EndpointsRegister {

    val authorizationEndpoint: AuthorizationEndpoint

    val smsLoginEndpoint: SMSLoginEndpoint

}

class EndpointsRegisterImpl(
    retrofit: Retrofit,
    panelRetrofit: Retrofit
) : EndpointsRegister {

    override val authorizationEndpoint: AuthorizationEndpoint =
        retrofit.create(AuthorizationEndpoint::class.java)

    override val smsLoginEndpoint: SMSLoginEndpoint =
        panelRetrofit.create(SMSLoginEndpoint::class.java)

}