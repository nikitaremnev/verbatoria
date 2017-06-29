package com.verbatoria.presentation.dashboard.presenter;

import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;
import com.verbatoria.presentation.dashboard.view.IDashboardView;
import com.verbatoria.utils.Logger;
import com.verbatoria.utils.rx.IRxSchedulers;

/**
 * Реализация презентера для dashboard
 *
 * @author nikitaremnev
 */
public class DashboardPresenter implements IDashboardPresenter {

    private static final String TAG = DashboardPresenter.class.getSimpleName();

    private IDashboardInteractor mDashboardInteractor;
    private IDashboardView mDashboardView;
    private IRxSchedulers mRxSchedulers;

    public DashboardPresenter(IDashboardInteractor dashboardInteractor,
                              IRxSchedulers rxSchedulers) {
        mDashboardInteractor = dashboardInteractor;
        mRxSchedulers = rxSchedulers;
    }

    @Override
    public void bindView(@NonNull IDashboardView dashboardView) {
        mDashboardView = dashboardView;
    }

    @Override
    public void unbindView() {
        mDashboardView = null;
    }

    @Override
    public void updateVerbatologInfo() {
        mDashboardInteractor.getVerbatologInfo()
                .subscribeOn(mRxSchedulers.getNewThreadScheduler())
                .observeOn(mRxSchedulers.getMainThreadScheduler())
                .subscribe(this::handleVerbatologInfoReceived, this::handleVerbatologInfoLoadingFailed);
    }

    private void handleVerbatologInfoReceived(@NonNull VerbatologInfoResponseModel verbatologInfoResponseModel) {
        Logger.e(TAG, verbatologInfoResponseModel.toString());
        mDashboardView.showVerbatologInfo(verbatologInfoResponseModel.toString());
    }

    private void handleVerbatologInfoLoadingFailed(Throwable throwable) {
        Logger.exc(TAG, throwable);
    }

}
