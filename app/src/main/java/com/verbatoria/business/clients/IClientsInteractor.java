package com.verbatoria.business.clients;

import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.data.network.response.MessageResponseModel;

import rx.Observable;

/**
 * @author nikitaremnev
 */

public interface IClientsInteractor {

    Observable<MessageResponseModel> addClient(ClientModel client);

    Observable<MessageResponseModel> editClient(ClientModel client);

    Observable<ClientModel> getClient(String clientId);

}
