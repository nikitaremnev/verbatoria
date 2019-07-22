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

//@GET(APIConstants.GET_EVENTS_URL)
//abstract fun getEventsRequest(
//    @Header(TOKEN_HEADER_KEY) accessToken: String,
//    @Query(FROM_TIME_QUERY_KEY) fromTime: String,
//    @Query(TO_TIME_QUERY_KEY) toTime: String
//): Observable<EventsResponseModel>
//
//@POST(APIConstants.ADD_EVENT_URL)
//abstract fun addEventRequest(
//    @Header(TOKEN_HEADER_KEY) accessToken: String,
//    @Body addEventRequestModel: AddEventRequestModel
//): Observable<EventResponseModel>
//
//@PUT(APIConstants.EDIT_EVENT_URL)
//abstract fun editEventRequest(
//    @Path(value = EVENT_ID_PATH_KEY) eventId: String,
//    @Header(TOKEN_HEADER_KEY) accessToken: String,
//    @Body editEventRequestModel: EditEventRequestModel
//): Observable<EventResponseModel>
//
//@DELETE(APIConstants.DELETE_EVENT_URL)
//abstract fun deleteEventRequest(
//    @Path(value = EVENT_ID_PATH_KEY) eventId: String,
//    @Header(TOKEN_HEADER_KEY) accessToken: String
//): Observable<ResponseBody>