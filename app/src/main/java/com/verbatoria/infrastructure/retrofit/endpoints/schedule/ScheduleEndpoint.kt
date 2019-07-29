package com.verbatoria.infrastructure.retrofit.endpoints.schedule

import com.verbatoria.infrastructure.retrofit.APIConstants
import com.verbatoria.infrastructure.retrofit.APIConstants.FROM_TIME_QUERY_KEY
import com.verbatoria.infrastructure.retrofit.APIConstants.PER_PAGE_KEY
import com.verbatoria.infrastructure.retrofit.APIConstants.TO_TIME_QUERY_KEY
import com.verbatoria.infrastructure.retrofit.endpoints.schedule.model.params.DeleteScheduleParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.schedule.model.params.SaveScheduleParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.schedule.model.response.ScheduleItemResponseDto
import com.verbatoria.infrastructure.retrofit.endpoints.schedule.model.response.ScheduleResponseDto
import retrofit2.http.*

/**
 * @author n.remnev
 */

interface ScheduleEndpoint {

    @GET(APIConstants.GET_SCHEDULE_URL)
    fun getSchedule(
        @Query(FROM_TIME_QUERY_KEY) fromTime: String,
        @Query(TO_TIME_QUERY_KEY) toTime: String,
        @Query(PER_PAGE_KEY) perPage: Int
    ): ScheduleResponseDto

    @POST(APIConstants.ADD_SCHEDULE_URL)
    fun saveSchedule(
        @Body params: SaveScheduleParamsDto
    ): List<ScheduleItemResponseDto>


    @DELETE(APIConstants.DELETE_SCHEDULE_URL)
    fun deleteSchedule(
        @Body params: DeleteScheduleParamsDto
    ): List<ScheduleItemResponseDto>

}