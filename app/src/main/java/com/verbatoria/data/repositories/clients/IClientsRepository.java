package com.verbatoria.data.repositories.clients;

import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.data.network.request.ClientRequestModel;
import com.verbatoria.data.network.response.MessageResponseModel;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * @author nikitaremnev
 */
public interface IClientsRepository {

    Observable<MessageResponseModel> addClient(String accessToken, ClientRequestModel clientRequestModel);

    Observable<ResponseBody> editClient(String clientId, String accessToken, ClientRequestModel clientRequestModel);

    Observable<ClientModel> getClient(String clientId, String accessToken);

}
