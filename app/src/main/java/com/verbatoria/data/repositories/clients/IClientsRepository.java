package com.verbatoria.data.repositories.clients;

import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.data.network.request.ClientRequestModel;
import com.verbatoria.data.network.request.EditClientRequestModel;
import com.verbatoria.data.network.response.ClientsResponseModel;

import io.reactivex.Observable;

/**
 * @author nikitaremnev
 */
public interface IClientsRepository {

    Observable<ClientModel> addClient(String accessToken, ClientRequestModel clientRequestModel);

    Observable<ClientModel> editClient(String clientId, String accessToken, EditClientRequestModel editClientRequestModel);

    Observable<ClientModel> getClient(String clientId, String accessToken);

    Observable<ClientsResponseModel> searchClients(String query, String accessToken);

}

