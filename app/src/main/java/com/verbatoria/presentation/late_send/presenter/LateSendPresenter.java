package com.verbatoria.presentation.late_send.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.verbatoria.business.late_send.ILateSendInteractor;
import com.verbatoria.business.late_send.models.LateReportModel;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.late_send.view.ILateSendView;

import java.util.List;

/**
 * Реализация презентера для экрана поздней отправки
 *
 * @author nikitaremnev
 */
public class LateSendPresenter extends BasePresenter implements ILateSendPresenter {

    private static final String TAG = LateSendPresenter.class.getSimpleName();

    private ILateSendInteractor mLateSendInteractor;
    private ISessionInteractor mSessionInteractor;
    private ILateSendView mLateSendView;

    private List<LateReportModel> mLateReportModels;

    private int mSelectedPosition;

    public LateSendPresenter(ILateSendInteractor lateSendInteractor,
                             ISessionInteractor sessionInteractor) {
        mLateSendInteractor = lateSendInteractor;
        mSessionInteractor = sessionInteractor;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLateSendInteractor.getLateReports()
                .doOnSubscribe(() -> mLateSendView.showProgress())
                .doOnUnsubscribe(() -> mLateSendView.hideProgress())
                .subscribe(this::handleLateReportsReceived, this::handleLateReportsError);
    }

    @Override
    public void saveState(Bundle outState) {

    }

    @Override
    public void restoreState(Bundle savedInstanceState) {

    }

    @Override
    public void bindView(@NonNull ILateSendView lateSendView) {
        mLateSendView = lateSendView;
    }

    @Override
    public void unbindView() {
        mLateSendView = null;
    }

    @Override
    public void sendReport(int position) {
        mSelectedPosition = position;
        mLateSendView.showProgress();
        mSessionInteractor.submitResults(mLateReportModels.get(mSelectedPosition).getReportFileName())
                .subscribe(this::handleResultsSubmitted, this::handleSendError);
    }

    private void handleLateReportsReceived(List<LateReportModel> lateReportModelList) {
        mLateReportModels = lateReportModelList;
        if (mLateReportModels.isEmpty()) {
            mLateSendView.showNoReportsToSend();
        } else {
            mLateSendView.showLateReports(mLateReportModels);
        }
    }

    private void handleSendError(Throwable throwable) {
        mLateSendView.hideProgress();
        mLateSendView.showError(throwable.getLocalizedMessage());
    }

    private void handleLateReportsError(Throwable throwable) {
        throwable.printStackTrace();
        mLateSendView.hideProgress();
        mLateSendView.showNoReportsToSend();
        mLateSendView.showError(throwable.getLocalizedMessage());
    }

    private void handleResultsSubmitted() {
        mSessionInteractor.finishSession(mLateReportModels.get(mSelectedPosition).getSessionId())
                .subscribe(this::handleSessionFinished, this::handleSendError);
    }

    private void handleSessionFinished() {
        mLateSendInteractor.cleanUp(mLateReportModels.get(mSelectedPosition))
                .subscribe(this::cleanUpFinished, this::handleSendError);
    }

    private void cleanUpFinished() {
        mLateSendView.hideProgress();
        mLateReportModels.remove(mSelectedPosition);
        mLateSendView.notifyItemSent(mSelectedPosition);
        if (mLateReportModels.size() == 0) {
            mLateSendView.showNoReportsToSend();
        }
    }

}
