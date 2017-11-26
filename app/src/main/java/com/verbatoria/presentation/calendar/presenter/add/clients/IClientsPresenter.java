package com.verbatoria.presentation.calendar.presenter.add.clients;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.presentation.calendar.view.add.clients.IClientsView;
import com.verbatoria.presentation.calendar.view.add.clients.search.ISearchClientsView;

/**
 * Презентер для добавления события в календарь
 *
 * @author nikitaremnev
 */
public interface IClientsPresenter {

    void bindView(@NonNull ISearchClientsView searchClientsView);
    void bindView(@NonNull IClientsView clientView);
    void unbindView();

    void obtainClient(Intent intent);
    ClientModel getClientModel();

    boolean isEditMode();

    void createClient();
    void editClient();
    void searchClients();

    String getClientName();
    String getClientPhone();
    String getClientEmail();
}
