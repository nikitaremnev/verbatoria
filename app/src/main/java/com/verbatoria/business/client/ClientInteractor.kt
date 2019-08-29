package com.verbatoria.business.client

import com.verbatoria.domain.authorization.manager.AuthorizationManager
import com.verbatoria.domain.client.model.Client
import com.verbatoria.domain.client.manager.ClientManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Single

/**
 * @author n.remnev
 */

interface ClientInteractor {

    fun createNewClient(client: Client): Single<String>

    fun editClient(client: Client): Single<Client>

    fun getCurrentCountry(): Single<String>

}

class ClientInteractorImpl(
    private val clientManager: ClientManager,
    private val authorizationManager: AuthorizationManager,
    private val schedulersFactory: RxSchedulersFactory
) : ClientInteractor {

    override fun createNewClient(client: Client): Single<String> =
        Single.fromCallable {
            clientManager.createNewClient(client)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun editClient(client: Client): Single<Client> =
        Single.fromCallable {
            clientManager.editClient(client)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun getCurrentCountry(): Single<String> =
        Single.fromCallable {
            authorizationManager.getCurrentCountry()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}