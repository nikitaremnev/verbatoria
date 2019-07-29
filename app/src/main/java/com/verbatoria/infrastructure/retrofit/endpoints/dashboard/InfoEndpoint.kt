package com.verbatoria.infrastructure.retrofit.endpoints.dashboard

import com.verbatoria.infrastructure.retrofit.APIConstants
import com.verbatoria.infrastructure.retrofit.APIConstants.LOCATION_ID_PATH_KEY
import com.verbatoria.infrastructure.retrofit.endpoints.dashboard.model.AgeGroupResponseDto
import com.verbatoria.infrastructure.retrofit.endpoints.dashboard.model.InfoResponseDto
import com.verbatoria.infrastructure.retrofit.endpoints.dashboard.model.LocationInfoResponseDto
import retrofit2.http.*

/**
 * @author n.remnev
 */


interface InfoEndpoint {

    @GET(APIConstants.VERBATOLOG_INFO_URL)
    fun getInfo(): InfoResponseDto

    @GET(APIConstants.GET_LOCATION_URL)
    fun getLocationInfo(
        @Path(LOCATION_ID_PATH_KEY) locationId: String
    ): LocationInfoResponseDto

    @GET(APIConstants.AGE_GROUPS_URL)
    fun getAgeGroupsForArchimedes(): List<AgeGroupResponseDto>


}