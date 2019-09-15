package com.verbatoria.ui.late_send.item

import com.remnev.verbatoria.R
import com.verbatoria.domain.late_send.model.LateSend
import com.verbatoria.domain.late_send.model.LateSendState
import com.verbatoria.infrastructure.extensions.formatToTime
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class LateReportBinder : ViewBinder<LateReportViewHolder, LateSend>() {

    override fun bind(view: LateReportViewHolder, data: LateSend, position: Int) {
        view.setChildNameAndAge(data.childName, data.childAge)
        view.setState(
            when (data.state) {
                LateSendState.HAS_NOTHING -> R.string.late_send_state_nothing
                LateSendState.HAS_DATA -> R.string.late_send_state_has_data
                LateSendState.HAS_QUESTIONNAIRE -> R.string.late_send_state_has_questionnaire
                LateSendState.DATA_SENT -> R.string.late_send_state_data_sent
                LateSendState.SESSION_FINISHED -> R.string.late_send_state_session_finished
                LateSendState.DATA_CLEANED -> R.string.late_send_state_data_cleaned
            }
        )
        view.setPeriod(data.startDate.formatToTime() + " - " + data.endDate.formatToTime())
    }

}