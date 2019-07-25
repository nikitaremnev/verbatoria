package com.verbatoria.infrastructure.retrofit.endpoints.client

import com.verbatoria.data.network.common.ClientModel
import com.verbatoria.infrastructure.retrofit.APIConstants
import com.verbatoria.infrastructure.retrofit.APIConstants.CLIENT_ID_PATH_KEY
import com.verbatoria.infrastructure.retrofit.APIConstants.QUERY_PATH_KEY
import com.verbatoria.infrastructure.retrofit.endpoints.client.model.params.CreateNewClientParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.client.model.params.EditClientParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.client.model.response.ClientResponseDto
import com.verbatoria.infrastructure.retrofit.endpoints.client.model.response.SearchClientResponseDto
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @author n.remnev
 */

interface ClientEndpoint {

    @GET(APIConstants.SEARCH_CLIENT_URL)
    fun searchClient(
        @Query(QUERY_PATH_KEY) query: String
    ): SearchClientResponseDto

    @POST(APIConstants.ADD_CLIENT_URL)
    fun createNewClient(
        @Body params: CreateNewClientParamsDto
    ): ClientResponseDto

    @PUT(APIConstants.EDIT_CLIENT_URL)
    fun editClient(
        @Path(value = CLIENT_ID_PATH_KEY) clientId: String,
        @Body params: EditClientParamsDto
    ): ClientResponseDto

    @GET(APIConstants.GET_CLIENT_URL)
    fun getClient(@Path(value = CLIENT_ID_PATH_KEY) clientId: String): ClientResponseDto

}