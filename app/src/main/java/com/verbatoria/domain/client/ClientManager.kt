package com.verbatoria.domain.client

import com.verbatoria.business.event.models.ClientModel
import com.verbatoria.infrastructure.retrofit.endpoints.client.ClientEndpoint

/**
 * @author n.remnev
 */

interface ClientManager {

    fun getClient(clientId: String): ClientModel

}

class ClientManagerImpl(
    private val clientEndpoint: ClientEndpoint
) : ClientManager {

    override fun getClient(clientId: String): ClientModel {
        val clientResponse = clientEndpoint.getClient(clientId)
        return ClientModel(
            id = clientId,
            name = clientResponse.name,
            email = clientResponse.email,
            phone = clientResponse.phone
        )
    }

}