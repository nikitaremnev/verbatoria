package com.verbatoria.infrastructure.retrofit.endpoints.event

import com.verbatoria.data.network.response.EventResponseModel
import com.verbatoria.infrastructure.retrofit.APIConstants.CREATE_NEW_EVENT_URL
import com.verbatoria.infrastructure.retrofit.APIConstants.DELETE_EVENT_URL
import com.verbatoria.infrastructure.retrofit.APIConstants.EDIT_EVENT_URL
import com.verbatoria.infrastructure.retrofit.APIConstants.EVENT_ID_PATH_KEY
import com.verbatoria.infrastructure.retrofit.endpoints.event.model.params.CreateNewOrEditEventParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.event.model.response.EventResponseDto
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * @author n.remnev
 */


interface EventEndpoint {

    @POST(CREATE_NEW_EVENT_URL)
    fun createNewEvent(
        @Body params: CreateNewOrEditEventParamsDto
    ): EventResponseDto

    @PUT(EDIT_EVENT_URL)
    fun editEvent(
        @Path(value = EVENT_ID_PATH_KEY) eventId: String,
        @Body params: CreateNewOrEditEventParamsDto
    ): EventResponseModel

    @DELETE(DELETE_EVENT_URL)
    fun deleteEvent(
        @Path(value = EVENT_ID_PATH_KEY) eventId: String
    ): ResponseBody

}