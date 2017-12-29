package com.verbatoria.presentation.late_send.view.adapter;

import com.verbatoria.business.late_send.models.LateReportModel;

/**
 * Binder для отображения ячейки поздней отправки отчета
 *
 * @author nikitaremnev
 */
public class LateReportHolderBinder {

    public static void bind(LateReportModel lateReportModel, LateReportViewHolder holder) {
        holder.setTitle(lateReportModel.getChildName());
        holder.setSubtitle(lateReportModel.getReportId());
    }

}
