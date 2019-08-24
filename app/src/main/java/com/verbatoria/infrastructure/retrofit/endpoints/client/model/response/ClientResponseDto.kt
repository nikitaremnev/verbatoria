package com.verbatoria.infrastructure.retrofit.endpoints.client.model.response

import com.verbatoria.infrastructure.retrofit.endpoints.child.model.response.ChildIdResponseDto

/**
 * @author n.remnev
 */

data class ClientResponseDto(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val children: List<ChildIdResponseDto>
)

