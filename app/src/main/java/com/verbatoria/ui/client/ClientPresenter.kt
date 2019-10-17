package com.verbatoria.ui.client

import com.verbatoria.business.client.ClientInteractor
import com.verbatoria.domain.client.model.Client
import com.verbatoria.ui.base.BasePresenter
import com.verbatoria.ui.event.EventDetailMode

/**
 * @author n.remnev
 */

class ClientPresenter(
    private val eventDetailMode: EventDetailMode,
    private var client: Client,
    private val clientInteractor: ClientInteractor
) : BasePresenter<ClientView>(), ClientView.Callback {

    private var editedClient: Client = client.copy()

    private var country: String = ""

    private var isPhoneMaskFilled: Boolean = false

    init {
        getCurrentCountry()
    }

    override fun onAttachView(view: ClientView) {
        super.onAttachView(view)
        view.setCurrentCountry(country)
        when {
            eventDetailMode.isCreateNew() -> {
                view.setEditableMode()
                checkIsAllFieldsFilled()
            }
            eventDetailMode.isStart() || eventDetailMode.isChildRequired() -> {
                view.setEditableMode()
                checkIsSomeFieldsChanged()
            }
            else -> view.setViewOnlyMode()
        }
        view.apply {
            setClientName(client.name)
            setClientPhone(client.phone)
            setClientEmail(client.email)
        }
        checkIsAllFieldsFilled()
    }

    //region ClientView.Callback

    override fun onClientNameChanged(newClientName: String) {
        if (eventDetailMode.isStart() || eventDetailMode.isChildRequired()) {
            editedClient.name = newClientName
            checkIsSomeFieldsChanged()
        } else if (eventDetailMode.isCreateNew()) {
            client.name = newClientName
            checkIsAllFieldsFilled()
        }
    }

    override fun onClientPhoneChanged(maskFilled: Boolean, newClientPhone: String) {
        isPhoneMaskFilled = maskFilled
        if (eventDetailMode.isStart() || eventDetailMode.isChildRequired()) {
            editedClient.phone = newClientPhone
            checkIsSomeFieldsChanged()
        } else if (eventDetailMode.isCreateNew()) {
            client.phone = newClientPhone
            checkIsAllFieldsFilled()
        }
    }

    override fun onClientEmailChanged(newClientEmail: String) {
        if (eventDetailMode.isStart() || eventDetailMode.isChildRequired()) {
            editedClient.email = newClientEmail
            checkIsSomeFieldsChanged()
        } else if (eventDetailMode.isCreateNew()) {
            client.email = newClientEmail
            checkIsAllFieldsFilled()
        }
    }

    override fun onSaveButtonClicked() {
        if (client.hasId()) {
            editClient()
        } else {
            createNewClient()
        }
    }

    override fun onBackPressed() {
        view?.close()
    }

    override fun onNavigationClicked() {
        view?.close()
    }

    //endregion

    private fun checkIsAllFieldsFilled() {
        if (client.hasName() &&
            client.hasPhone() && isPhoneMaskFilled &&
            client.hasEmail()) {
            view?.setSaveButtonEnabled()
        } else {
            view?.setSaveButtonDisabled()
        }
    }

    private fun checkIsSomeFieldsChanged() {
        if (client.name != editedClient.name || client.phone != editedClient.phone ||
            client.email != editedClient.email && editedClient.hasEmail()) {
            view?.setSaveButtonEnabled()
        } else {
            view?.setSaveButtonDisabled()
        }
    }

    private fun createNewClient() {
        view?.showSaveProgress()
        clientInteractor.createNewClient(client)
            .doAfterTerminate {
                view?.hideSaveProgress()
            }
            .subscribe({ clientId ->
                client.id = clientId
                view?.close(client)
            }, { error ->
                view?.showErrorSnackbar(error.localizedMessage ?: "Create new client error occurred")
            })
            .let(::addDisposable)
    }

    private fun editClient() {
        view?.showSaveProgress()
        clientInteractor.editClient(editedClient)
            .doAfterTerminate {
                view?.hideSaveProgress()
            }
            .subscribe({ editedClient ->
                client = editedClient
                view?.close(editedClient)
            }, { error ->
                view?.showErrorSnackbar(error.localizedMessage ?: "Edit client error occurred")
            })
            .let(::addDisposable)
    }

    private fun getCurrentCountry() {
        clientInteractor.getCurrentCountry()
            .subscribe({ country ->
                this.country = country
                view?.setCurrentCountry(this.country)
            }, { error ->
                view?.showErrorSnackbar(error.message ?: "Get current country error occurred")
            })
            .let(::addDisposable)
    }

}