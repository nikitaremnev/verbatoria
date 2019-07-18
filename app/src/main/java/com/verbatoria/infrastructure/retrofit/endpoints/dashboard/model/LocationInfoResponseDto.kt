package com.verbatoria.infrastructure.retrofit.endpoints.dashboard.model

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class LocationInfoResponseDto(
    val id: String,
    val name: String,
    @SerializedName("school")
    val isSchool: Boolean?,
    val address: String,
    val locale: String,
    @SerializedName("available_locales_array")
    val availableLocales: List<String>,
    val partner: PartnerResponseDto,
    val city: CityResponseDto
)