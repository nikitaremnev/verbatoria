package com.verbatoria.domain.client.manager

import com.verbatoria.domain.client.model.Client
import com.verbatoria.infrastructure.retrofit.endpoints.client.ClientEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.client.model.params.ClientParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.client.model.params.CreateNewClientParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.client.model.params.EditClientParamsDto
import java.lang.IllegalStateException

/**
 * @author n.remnev
 */

interface ClientManager {

    fun getClient(clientId: String): Client

    fun createNewClient(client: Client): String

    fun editClient(client: Client): Client

}

class ClientManagerImpl(
    private val clientEndpoint: ClientEndpoint
) : ClientManager {

    override fun getClient(clientId: String): Client {
        val clientResponse = clientEndpoint.getClient(clientId)
        return Client(
            id = clientId,
            name = clientResponse.name,
            email = clientResponse.email,
            phone = clientResponse.phone
        )
    }

    override fun createNewClient(client: Client): String {
        val createNewClientResponse = clientEndpoint.createNewClient(
            CreateNewClientParamsDto(
                name = client.name,
                phone = client.phone,
                email = client.email,
                phoneCountry = "ru"
            )
        )
        return createNewClientResponse.id
    }

    override fun editClient(client: Client): Client {
        val editClientResponse = clientEndpoint.editClient(
            clientId = client.id ?: throw IllegalStateException("try to edit client without id"),
            params = EditClientParamsDto(
                ClientParamsDto(
                    name = client.name,
                    phone = client.phone,
                    email = client.email,
                    phoneCountry = "ru"
                )
            )
        )
        return Client(
            id = editClientResponse.id,
            name = editClientResponse.name,
            email = editClientResponse.email,
            phone = editClientResponse.phone
        )
    }

}