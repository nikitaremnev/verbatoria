package com.verbatoria.presentation.dashboard.presenter;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.business.token.models.UserStatus;

/**
 * Реализация презентера для dashboard
 *
 * @author nikitaremnev
 */
public class DashboardPresenter implements IDashboardPresenter {

    private static final String TAG = DashboardPresenter.class.getSimpleName();

    private IDashboardInteractor mDashboardInteractor;

    public DashboardPresenter(IDashboardInteractor dashboardInteractor) {
        mDashboardInteractor = dashboardInteractor;
    }

    @Override
    public boolean isBlocked() {
        return mDashboardInteractor.getUserStatus().equals(UserStatus.BLOCKED_STATUS);
    }

    @Override
    public boolean isShowSettings() {
        return mDashboardInteractor.isShowSettings();
    }

    @Override
    public void setShowSettings(boolean showSettings) {
        mDashboardInteractor.setShowSettings(showSettings);
    }

}
