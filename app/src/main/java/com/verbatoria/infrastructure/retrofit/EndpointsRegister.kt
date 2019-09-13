package com.verbatoria.infrastructure.retrofit

import com.verbatoria.infrastructure.retrofit.endpoints.authorization.AuthorizationEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.authorization.SMSLoginEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.child.ChildEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.client.ClientEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.dashboard.CalendarEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.dashboard.InfoEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.event.EventEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.report.ReportEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.schedule.ScheduleEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.submit.SubmitEndpoint
import retrofit2.Retrofit

/**
 * @author n.remnev
 */

interface EndpointsRegister {

    val authorizationEndpoint: AuthorizationEndpoint

    val smsLoginEndpoint: SMSLoginEndpoint

    val infoEndpoint: InfoEndpoint

    val calendarEndpoint: CalendarEndpoint

    val childEndpoint: ChildEndpoint

    val clientEndpoint: ClientEndpoint

    val eventEndpoint: EventEndpoint

    val scheduleEndpoint: ScheduleEndpoint

    val reportEndpoint: ReportEndpoint

    val submitEndpoint: SubmitEndpoint

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

    override val childEndpoint: ChildEndpoint=
        retrofit.create(ChildEndpoint::class.java)

    override val clientEndpoint: ClientEndpoint =
        retrofit.create(ClientEndpoint::class.java)

    override val eventEndpoint: EventEndpoint =
        retrofit.create(EventEndpoint::class.java)

    override val scheduleEndpoint: ScheduleEndpoint =
        retrofit.create(ScheduleEndpoint::class.java)

    override val reportEndpoint: ReportEndpoint =
        retrofit.create(ReportEndpoint::class.java)

    override val submitEndpoint: SubmitEndpoint =
        retrofit.create(SubmitEndpoint::class.java)

}