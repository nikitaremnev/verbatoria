package com.verbatoria.presentation.dashboard.presenter;

import android.support.annotation.NonNull;
import com.verbatoria.business.token.interactor.ITokenInteractor;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.presentation.dashboard.view.IDashboardView;
import com.verbatoria.utils.Logger;
import com.verbatoria.utils.rx.IRxSchedulers;
import com.verbatoria.utils.rx.RxSchedulers;

/**
 * Реализация презентера для dashboard
 *
 * @author nikitaremnev
 */
public class DashboardPresenter implements IDashboardPresenter {

    private static final String TAG = DashboardPresenter.class.getSimpleName();

    private ITokenInteractor mTokenInteractor;
    private IDashboardView mDashboardView;

    IRxSchedulers mRxSchedulers;

    public DashboardPresenter(ITokenInteractor tokenInteractor) {
        this.mTokenInteractor = tokenInteractor;
        mRxSchedulers = new RxSchedulers();
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
    public void readToken() {
        mDashboardView.showProgress();
        mTokenInteractor.getToken()
                .subscribeOn(mRxSchedulers.getMainThreadScheduler())
                .observeOn(mRxSchedulers.getMainThreadScheduler())
                .subscribe(this::handleTokenReceived, this::handleTokenError);
    }

    private void handleTokenReceived(@NonNull TokenModel tokenModel) {
        Logger.e(TAG, "handleTokenReceived");
        mDashboardView.hideProgress();
        mDashboardView.showToken(tokenModel);
    }

    private void handleTokenError(Throwable throwable) {
        Logger.e(TAG, "handleTokenError");
        mDashboardView.hideProgress();
    }
}
