package com.verbatoria.di.client

import com.verbatoria.business.client.ClientInteractorImpl
import com.verbatoria.business.client.Client
import com.verbatoria.domain.authorization.AuthorizationManager
import com.verbatoria.domain.client.ClientManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.ui.client.ClientPresenter
import com.verbatoria.ui.event.EventDetailMode
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class ClientModule {

    @Provides
    @Reusable
    fun provideClientPresenter(
        eventDetailModeOrdinal: Int,
        client: Client?,
        clientManager: ClientManager,
        authorizationManager: AuthorizationManager,
        rxSchedulersFactory: RxSchedulersFactory
    ): ClientPresenter =
        ClientPresenter(
            EventDetailMode.valueOf(eventDetailModeOrdinal),
            client ?: Client(),
            ClientInteractorImpl(
                clientManager,
                authorizationManager,
                rxSchedulersFactory
            )
        )

}