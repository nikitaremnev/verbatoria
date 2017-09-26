package com.verbatoria.presentation.calendar.presenter.add.clients;

import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.presentation.calendar.view.add.clients.IClientsView;

/**
 * Реализация презентера для экрана добавления события
 *
 * @author nikitaremnev
 */
public class ClientsPresenter implements IClientsPresenter {

    private IDashboardInteractor mDashboardInteractor;
    private IClientsView mClientView;

    public ClientsPresenter(IDashboardInteractor dashboardInteractor) {
        mDashboardInteractor = dashboardInteractor;
    }

    @Override
    public void bindView(@NonNull IClientsView clientView) {
        mClientView = clientView;
    }

    @Override
    public void unbindView() {
        mClientView = null;
    }

}
