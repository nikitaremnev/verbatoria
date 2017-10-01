package com.verbatoria.presentation.calendar.presenter.add.clients;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.verbatoria.business.clients.IClientsInteractor;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.view.add.clients.IClientsView;

/**
 * Реализация презентера для экрана данных о клиенте
 *
 * @author nikitaremnev
 */
public class ClientsPresenter extends BasePresenter implements IClientsPresenter {

    private IClientsInteractor mClientsInteractor;
    private IClientsView mClientView;

    public ClientsPresenter(IClientsInteractor clientsInteractor) {
        mClientsInteractor = clientsInteractor;
    }

    @Override
    public void bindView(@NonNull IClientsView clientView) {
        mClientView = clientView;
    }

    @Override
    public void unbindView() {
        mClientView = null;
    }

    @Override
    public void saveState(Bundle outState) {

    }

    @Override
    public void restoreState(Bundle savedInstanceState) {

    }
}
