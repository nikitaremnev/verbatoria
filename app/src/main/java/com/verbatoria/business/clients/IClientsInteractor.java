package com.verbatoria.business.clients;

import com.verbatoria.data.network.common.ClientModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author nikitaremnev
 */

public interface IClientsInteractor {

    Observable<ClientModel> addClient(ClientModel client);

    Observable<ClientModel> editClient(ClientModel client);

    Observable<ClientModel> getClient(String clientId);

    Observable<List<ClientModel>> searchClients(String query);

    String getCountry();
}
