package com.verbatoria.business.clients;

import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.data.network.request.ClientRequestModel;
import com.verbatoria.data.network.request.EditClientRequestModel;
import com.verbatoria.data.repositories.clients.IClientsRepository;
import com.verbatoria.utils.CountriesHelper;
import com.verbatoria.utils.PreferencesStorage;
import com.verbatoria.utils.RxSchedulers;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author nikitaremnev
 */
public class ClientsInteractor implements IClientsInteractor {

    private static final String TAG = ClientsInteractor.class.getSimpleName();

    private IClientsRepository mClientsRepository;

    public ClientsInteractor(IClientsRepository clientsRepository) {
        mClientsRepository = clientsRepository;
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

    @Override
    public Observable<List<ClientModel>> searchClients(String query) {
        return mClientsRepository.searchClients(query, getAccessToken())
                .map(clientsResponseModel -> clientsResponseModel.getClients())
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public String getCountry() {
        return PreferencesStorage.getInstance().getCountry();
    }

    private ClientRequestModel getClientRequestModel(ClientModel client) {
        return new ClientRequestModel()
                .setName(client.getName())
                .setPhone(client.getPhone())
                .setEmail(client.getEmail())
                .setPhoneCountry(CountriesHelper.getCountryCodeByCountryName(PreferencesStorage.getInstance().getCountry()));
    }

    private EditClientRequestModel getEditClientRequestModel(ClientModel client) {
        return new EditClientRequestModel()
                .setClient(getClientRequestModel(client));
    }

    private String getAccessToken() {
        return "";
    }

}