package com.verbatoria.infrastructure.retrofit.endpoints.child.model.response

import com.verbatoria.infrastructure.retrofit.endpoints.client.model.response.ClientResponseDto

/**
 * @author n.remnev
 */

data class SearchChildResponseDto(
    val data: List<ClientResponseDto>
)

