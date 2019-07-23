package com.verbatoria.business.dashboard.info.models

import com.verbatoria.business.user.UserStatus

/**
 * @author n.remnev
 */

data class InfoModel(
    val name: String,
    val phone: String,
    val email: String,
    val status: UserStatus,
    val isArchimedesAllowed: Boolean,
    val locationId: String
)