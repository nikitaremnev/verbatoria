package com.verbatoria.infrastructure.retrofit.endpoints.child

import com.verbatoria.infrastructure.retrofit.APIConstants
import com.verbatoria.infrastructure.retrofit.APIConstants.CHILD_ID_PATH_KEY
import com.verbatoria.infrastructure.retrofit.APIConstants.CLIENT_ID_PATH_KEY
import com.verbatoria.infrastructure.retrofit.endpoints.child.model.response.ChildResponseDto
import com.verbatoria.infrastructure.retrofit.endpoints.child.model.response.SearchChildResponseDto
import com.verbatoria.infrastructure.retrofit.endpoints.child.model.params.CreateOrEditChildParamsDto
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * @author n.remnev
 */

interface ChildEndpoint {

    @GET(APIConstants.SEARCH_CHILD_URL)
    fun searchChild(
        @Query(APIConstants.QUERY_PATH_KEY) query: String
    ): SearchChildResponseDto

    @POST(APIConstants.ADD_CHILD_URL)
    fun createNewChild(
        @Path(value = CLIENT_ID_PATH_KEY) clientId: String,
        @Body params: CreateOrEditChildParamsDto
    ): ChildResponseDto

    @PUT(APIConstants.EDIT_CHILD_URL)
    fun editChild(
        @Path(value = CLIENT_ID_PATH_KEY) clientId: String,
        @Path(value = CHILD_ID_PATH_KEY) childId: String,
        @Body params: CreateOrEditChildParamsDto
    ): ResponseBody

    @GET(APIConstants.GET_CHILD_URL)
    fun getChild(
        @Path(value = CLIENT_ID_PATH_KEY) clientId: String,
        @Path(value = CHILD_ID_PATH_KEY) childId: String
    ): ChildResponseDto

}