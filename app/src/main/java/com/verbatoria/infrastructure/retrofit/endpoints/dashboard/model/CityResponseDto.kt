package com.verbatoria.infrastructure.retrofit.endpoints.dashboard.model

/**
 * @author n.remnev
 */

data class CityResponseDto(
    val id: String,
    val name: String,
    val country: CountryResponseDto
)