package com.verbatoria.presentation.late_send.item

import com.verbatoria.business.late_send.models.LateReportModel
import com.verbatoria.presentation.common.ViewBinder

/**
 * @author n.remnev
 */

class LateReportBinder : ViewBinder<LateReportViewHolder, LateReportModel>() {

    override fun bind(view: LateReportViewHolder, data: LateReportModel, position: Int) {
        view.setTitle(data.childName)
        view.setSubtitle(data.reportId)
    }

}