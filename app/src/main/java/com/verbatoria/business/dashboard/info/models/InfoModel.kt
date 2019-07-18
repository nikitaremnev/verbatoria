package com.verbatoria.business.dashboard.info.models

/**
 * @author n.remnev
 */

data class InfoModel(
    val name: String,
    val phone: String,
    val email: String,
    val isArchimedesAllowed: Boolean,
    val locationId: String
)