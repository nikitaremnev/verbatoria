package com.verbatoria.business.clients;

import com.verbatoria.data.repositories.clients.IClientsRepository;

/**
 * @author nikitaremnev
 */

public class ClientsInteractor implements IClientsInteractor {

    private static final String TAG = ClientsInteractor.class.getSimpleName();

    private IClientsRepository mClientsRepository;

    public ClientsInteractor(IClientsRepository clientsRepository) {
        mClientsRepository = clientsRepository;
    }

}