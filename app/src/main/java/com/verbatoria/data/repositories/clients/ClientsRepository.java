package com.verbatoria.data.repositories.clients;

import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.data.network.request.ClientRequestModel;
import com.verbatoria.data.network.request.EditClientRequestModel;
import com.verbatoria.data.network.response.ClientsResponseModel;

import io.reactivex.Observable;

/**
 * @author nikitaremnev
 */
public class ClientsRepository implements IClientsRepository {

    public ClientsRepository() {

    }

    @Override
    public Observable<ClientModel> addClient(String accessToken, ClientRequestModel clientRequestModel) {
        return APIFactory.getAPIService().addClientRequest(accessToken, clientRequestModel);
    }

    @Override
    public Observable<ClientModel> editClient(String clientId, String accessToken, EditClientRequestModel editClientRequestModel) {
        return APIFactory.getAPIService().editClientRequest(clientId, accessToken, editClientRequestModel);
    }

    @Override
    public Observable<ClientModel> getClient(String clientId, String accessToken) {
        return APIFactory.getAPIService().getClientRequest(clientId, accessToken);
    }

    @Override
    public Observable<ClientsResponseModel> searchClients(String query, String accessToken) {
        return APIFactory.getAPIService().findClientRequest(query, accessToken);
    }
}
