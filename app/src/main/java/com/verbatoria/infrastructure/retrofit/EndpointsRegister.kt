package com.verbatoria.infrastructure.retrofit

import com.verbatoria.infrastructure.retrofit.endpoints.authorization.AuthorizationEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.SMSLoginEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.client.ClientEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.dashboard.CalendarEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.dashboard.InfoEndpoint
import retrofit2.Retrofit

/**
 * @author n.remnev
 */

interface EndpointsRegister {

    val authorizationEndpoint: AuthorizationEndpoint

    val smsLoginEndpoint: SMSLoginEndpoint

    val infoEndpoint: InfoEndpoint

    val calendarEndpoint: CalendarEndpoint

    val clientEndpoint: ClientEndpoint


}

class EndpointsRegisterImpl(
    retrofit: Retrofit,
    panelRetrofit: Retrofit
) : EndpointsRegister {

    override val authorizationEndpoint: AuthorizationEndpoint =
        retrofit.create(AuthorizationEndpoint::class.java)

    override val smsLoginEndpoint: SMSLoginEndpoint =
        panelRetrofit.create(SMSLoginEndpoint::class.java)

    override val infoEndpoint: InfoEndpoint =
        retrofit.create(InfoEndpoint::class.java)

    override val calendarEndpoint: CalendarEndpoint =
        retrofit.create(CalendarEndpoint::class.java)

    override val clientEndpoint: ClientEndpoint =
        retrofit.create(ClientEndpoint::class.java)

}