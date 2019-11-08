package com.verbatoria.business.dashboard.info.models

/**
 * @author n.remnev
 */

data class LocationInfoModel(
    val id: String,
    val name: String,
    val address: String,
    val point: String,
    var currentLocale: String = "",
    val updatedLocale: String
)