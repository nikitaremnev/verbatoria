package com.verbatoria.business.late_send

import java.util.*

/**
 * @author n.remnev
 */

data class LateSendItemModel(
    private val sessionId: String,
    private val startDate: Date,
    private val endDate: Date,
    private val reportId: String,
    private val isInstantReport: Boolean,
    private val isArchimed: Boolean,
    private val isHobby: Boolean,
    private val sendingStatus: SendingStatus
) {

    enum class SendingStatus {

    }

}