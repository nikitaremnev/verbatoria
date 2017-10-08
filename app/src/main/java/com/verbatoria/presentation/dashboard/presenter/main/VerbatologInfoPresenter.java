package com.verbatoria.presentation.dashboard.presenter.main;

import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.business.dashboard.models.LocationModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.presentation.dashboard.view.info.IVerbatologInfoView;
import com.verbatoria.utils.Logger;

/**
 * Реализация презентера для dashboard
 *
 * @author nikitaremnev
 */
public class VerbatologInfoPresenter implements IVerbatologInfoPresenter {

    private static final String TAG = VerbatologInfoPresenter.class.getSimpleName();

    private IDashboardInteractor mDashboardInteractor;
    private IVerbatologInfoView mVerbatologInfoView;
    private VerbatologModel mVerbatologModel;

    public VerbatologInfoPresenter(IDashboardInteractor dashboardInteractor) {
        mDashboardInteractor = dashboardInteractor;
    }

    @Override
    public void bindView(@NonNull IVerbatologInfoView verbatologInfoView) {
        mVerbatologInfoView = verbatologInfoView;
        mVerbatologModel = new VerbatologModel();
    }

    @Override
    public void unbindView() {
        mVerbatologInfoView = null;
        mVerbatologModel = null;
    }

    @Override
    public void updateVerbatologInfo() {
        mDashboardInteractor.getVerbatologInfo(mVerbatologModel)
                .subscribe(this::handleVerbatologInfoReceived, this::handleVerbatologInfoLoadingFailed);
    }

    @Override
    public void updateLocationInfo() {
        mDashboardInteractor.getLocation()
                .subscribe(this::handleLocationInfoReceived, this::handleLocationInfoLoadingFailed);
    }

    private void handleVerbatologInfoReceived(@NonNull VerbatologModel verbatologModel) {
        Logger.e(TAG, verbatologModel.toString());
        updateLocationInfo();
        mVerbatologInfoView.showVerbatologInfo(verbatologModel.getFullName(), verbatologModel.getPhone(), verbatologModel.getEmail());
    }

    private void handleVerbatologInfoLoadingFailed(Throwable throwable) {
        throwable.printStackTrace();
        mDashboardInteractor.getVerbatologInfoFromCache()
                .subscribe(this::handleVerbatologInfoReceived, this::handleVerbatologInfoLoadingFailed);
    }

    private void handleLocationInfoReceived(@NonNull LocationModel locationModel) {
        Logger.e(TAG, locationModel.toString());
        mVerbatologInfoView.showLocationInfo(locationModel);
    }

    private void handleLocationInfoLoadingFailed(Throwable throwable) {
        throwable.printStackTrace();
        Logger.exc(TAG, throwable);
    }

}
