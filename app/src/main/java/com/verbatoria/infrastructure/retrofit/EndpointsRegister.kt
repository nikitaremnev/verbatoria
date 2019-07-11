package com.verbatoria.infrastructure.retrofit

import com.verbatoria.infrastructure.retrofit.endpoints.authorization.AuthorizationEndpoint
import retrofit2.Retrofit

/**
 * @author n.remnev
 */

interface EndpointsRegister {

    val authorizationEndpoint: AuthorizationEndpoint

}

class EndpointsRegisterImpl(
    retrofit: Retrofit
) : EndpointsRegister {

    override val authorizationEndpoint: AuthorizationEndpoint = retrofit.create(AuthorizationEndpoint::class.java)

}