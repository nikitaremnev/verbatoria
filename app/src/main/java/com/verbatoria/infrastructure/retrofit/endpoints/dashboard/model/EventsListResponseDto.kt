package com.verbatoria.infrastructure.retrofit.endpoints.dashboard.model

import com.verbatoria.infrastructure.retrofit.endpoints.event.model.response.EventResponseDto

/**
 * @author n.remnev
 */

data class EventsListResponseDto(
    val data: List<EventResponseDto>
)