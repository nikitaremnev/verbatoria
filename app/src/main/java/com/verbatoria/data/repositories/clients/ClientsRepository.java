package com.verbatoria.data.repositories.clients;

import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.data.network.request.ClientRequestModel;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * @author nikitaremnev
 */
public class ClientsRepository implements IClientsRepository {

    public ClientsRepository() {

    }

    @Override
    public Observable<ResponseBody> addClient(String clientId, String accessToken, ClientRequestModel clientRequestModel) {
        return APIFactory.getAPIService().addClientRequest(clientId, accessToken, clientRequestModel);
    }

    @Override
    public Observable<ResponseBody> editClient(String clientId, String accessToken, ClientRequestModel clientRequestModel) {
        return APIFactory.getAPIService().editClientRequest(clientId, accessToken, clientRequestModel);
    }

    @Override
    public Observable<ClientModel> getClient(String clientId, String accessToken) {
        return APIFactory.getAPIService().getClientRequest(clientId, accessToken);
    }
}
