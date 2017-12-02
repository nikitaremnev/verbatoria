package com.verbatoria.presentation.dashboard.presenter.settings;

import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.presentation.dashboard.view.settings.ISettingsView;

/**
 * Реализация презентера для settings
 *
 * @author nikitaremnev
 */
public class SettingsPresenter implements ISettingsPresenter {

    private static final String TAG = SettingsPresenter.class.getSimpleName();

    private IDashboardInteractor mDashboardInteractor;
    private ISettingsView mSettingsView;

    public SettingsPresenter(IDashboardInteractor dashboardInteractor) {
        mDashboardInteractor = dashboardInteractor;
    }

    @Override
    public void bindView(@NonNull ISettingsView settingsView) {
        mSettingsView = settingsView;
    }

    @Override
    public void unbindView() {
        mSettingsView = null;
    }

//    @Override
//    public void onConnectionClicked() {
//        mSettingsView.showConnection()
//    }

    @Override
    public void onDeveloperInfoClicked() {
        mSettingsView.showDeveloperInfo(
                mDashboardInteractor.getApplicationVersion(),
                mDashboardInteractor.getAndroidVersion()
        );
    }

    @Override
    public void onQuitClicked() {
        mSettingsView.showLogin();
    }

}
