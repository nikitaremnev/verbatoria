package com.verbatoria.data.repositories.clients;

import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.data.network.request.ClientRequestModel;
import com.verbatoria.data.network.request.EditClientRequestModel;
import com.verbatoria.data.network.response.MessageResponseModel;

import rx.Observable;

/**
 * @author nikitaremnev
 */
public interface IClientsRepository {

    Observable<MessageResponseModel> addClient(String accessToken, ClientRequestModel clientRequestModel);

    Observable<ClientModel> editClient(String clientId, String accessToken, EditClientRequestModel editClientRequestModel);

    Observable<ClientModel> getClient(String clientId, String accessToken);

}
