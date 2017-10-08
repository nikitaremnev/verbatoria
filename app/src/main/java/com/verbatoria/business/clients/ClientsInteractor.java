package com.verbatoria.business.clients;

import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.data.network.request.ClientRequestModel;
import com.verbatoria.data.network.request.EditClientRequestModel;
import com.verbatoria.data.repositories.clients.IClientsRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.utils.RxSchedulers;

import rx.Observable;

/**
 * @author nikitaremnev
 */
public class ClientsInteractor implements IClientsInteractor {

    private static final String TAG = ClientsInteractor.class.getSimpleName();

    private IClientsRepository mClientsRepository;
    private ITokenRepository mTokenRepository;

    public ClientsInteractor(IClientsRepository clientsRepository,
                             ITokenRepository tokenRepository) {
        mClientsRepository = clientsRepository;
        mTokenRepository = tokenRepository;
    }


    @Override
    public Observable<ClientModel> addClient(ClientModel client) {
        return mClientsRepository.addClient(getAccessToken(), getClientRequestModel(client))
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Observable<ClientModel> editClient(ClientModel client) {
        return mClientsRepository.editClient(client.getId(), getAccessToken(), getEditClientRequestModel(client))
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Observable<ClientModel> getClient(String clientId) {
        return mClientsRepository.getClient(clientId, getAccessToken())
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    private ClientRequestModel getClientRequestModel(ClientModel client) {
        return new ClientRequestModel()
                .setName(client.getName())
                .setPhone(client.getPhone())
                .setEmail(client.getEmail());
    }

    private EditClientRequestModel getEditClientRequestModel(ClientModel client) {
        return new EditClientRequestModel()
                .setClient(getClientRequestModel(client));
    }

    private String getAccessToken() {
        TokenModel tokenModel = mTokenRepository.getToken();
        return tokenModel.getAccessToken();
    }

}