package com.verbatoria.infrastructure.retrofit.endpoints.dashboard

import com.verbatoria.infrastructure.retrofit.APIConstants
import com.verbatoria.infrastructure.retrofit.APIConstants.FROM_TIME_QUERY_KEY
import com.verbatoria.infrastructure.retrofit.APIConstants.TO_TIME_QUERY_KEY
import com.verbatoria.infrastructure.retrofit.endpoints.dashboard.model.EventsListResponseDto
import retrofit2.http.*

/**
 * @author n.remnev
 */


interface CalendarEndpoint {

    @GET(APIConstants.GET_EVENTS_URL)
    fun getEvents(
        @Query(FROM_TIME_QUERY_KEY) fromTime: String,
        @Query(TO_TIME_QUERY_KEY) toTime: String
    ): EventsListResponseDto


}