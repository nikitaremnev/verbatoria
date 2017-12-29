package com.verbatoria.presentation.late_send.view;

import com.verbatoria.business.late_send.models.LateReportModel;

import java.util.List;

/**
 *
 * View для экрана поздней отправки отчетов
 *
 * @author nikitaremnev
 */
public interface ILateSendView {

    void showProgress();

    void hideProgress();

    void notifyItemSent(int index);

    void showError(String error);

    void showNoReportsToSend();

    void showLateReports(List<LateReportModel> lateReportModels);

    interface Callback {
        void onItemClicked(int position);
    }

}
