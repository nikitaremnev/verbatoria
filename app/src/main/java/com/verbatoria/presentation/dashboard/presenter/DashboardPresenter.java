package com.verbatoria.presentation.dashboard.presenter;

import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.data.network.response.VerbatologEventResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;
import com.verbatoria.presentation.dashboard.view.IDashboardView;
import com.verbatoria.utils.Logger;
import com.verbatoria.utils.rx.IRxSchedulers;

import java.util.List;

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
    private VerbatologModel mVerbatologModel;

    public DashboardPresenter(IDashboardInteractor dashboardInteractor,
                              IRxSchedulers rxSchedulers) {
        mDashboardInteractor = dashboardInteractor;
        mRxSchedulers = rxSchedulers;
    }

    @Override
    public void bindView(@NonNull IDashboardView dashboardView) {
        mDashboardView = dashboardView;
        mVerbatologModel = new VerbatologModel();
    }

    @Override
    public void unbindView() {
        mDashboardView = null;
        mVerbatologModel = null;
    }

    @Override
    public void updateVerbatologInfo() {
        mDashboardInteractor.getVerbatologInfo(mVerbatologModel)
                .subscribeOn(mRxSchedulers.getNewThreadScheduler())
                .observeOn(mRxSchedulers.getMainThreadScheduler())
                .subscribe(this::handleVerbatologInfoReceived, this::handleVerbatologInfoLoadingFailed);
    }

    @Override
    public void updateVerbatologEvents() {
        mDashboardInteractor.getVerbatologEvents(mVerbatologModel)
                .subscribeOn(mRxSchedulers.getNewThreadScheduler())
                .observeOn(mRxSchedulers.getMainThreadScheduler())
                .subscribe(this::handleVerbatologEventsReceived, this::handleVerbatologEventsLoadingFailed);
    }

    private void handleVerbatologInfoReceived(@NonNull VerbatologModel verbatologModel) {
        Logger.e(TAG, verbatologModel.toString());
        mDashboardView.showVerbatologInfo(verbatologModel.toString());
    }

    private void handleVerbatologInfoLoadingFailed(Throwable throwable) {
        Logger.exc(TAG, throwable);
    }

    private void handleVerbatologEventsReceived(@NonNull VerbatologModel verbatologModel) {
        Logger.e(TAG, verbatologModel.toString());
        mDashboardView.showVerbatologEvents(verbatologModel.toString());
    }

    private void handleVerbatologEventsLoadingFailed(Throwable throwable) {
        Logger.exc(TAG, throwable);
    }
}
