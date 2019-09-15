package com.verbatoria.domain.late_send.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author n.remnev
 */

@Parcelize
data class LateSend(
    var eventId: String,
    var sessionId: String,
    var childName: String,
    var childAge: Int,
    var startDate: Date,
    var endDate: Date,
    var state: LateSendState
) : Parcelable

enum class LateSendState {

    HAS_NOTHING, HAS_DATA, HAS_QUESTIONNAIRE, DATA_SENT, SESSION_FINISHED, DATA_CLEANED;

    companion object {

        fun valueOf(ordinal: Int) =
            when (ordinal) {
                HAS_NOTHING.ordinal -> HAS_NOTHING
                HAS_DATA.ordinal -> HAS_DATA
                HAS_QUESTIONNAIRE.ordinal -> HAS_QUESTIONNAIRE
                DATA_SENT.ordinal -> DATA_SENT
                SESSION_FINISHED.ordinal -> SESSION_FINISHED
                DATA_CLEANED.ordinal -> DATA_CLEANED
                else -> throw IllegalArgumentException("Wrong ordinal for LateSendState")
            }

    }

}