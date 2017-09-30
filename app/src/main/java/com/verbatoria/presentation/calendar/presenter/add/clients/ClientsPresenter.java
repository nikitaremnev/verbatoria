package com.verbatoria.presentation.calendar.presenter.add.clients;

import android.support.annotation.NonNull;

import com.verbatoria.business.clients.IClientsInteractor;
import com.verbatoria.presentation.calendar.view.add.clients.IClientsView;

/**
 * Реализация презентера для экрана данных о клиенте
 *
 * @author nikitaremnev
 */
public class ClientsPresenter implements IClientsPresenter {

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

}
