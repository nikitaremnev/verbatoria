package com.verbatoria.ui.client

import com.verbatoria.business.client.ClientInteractor
import com.verbatoria.domain.client.Client
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

    private var country: String = ""

    init {
        getCurrentCountry()
    }

    override fun onAttachView(view: ClientView) {
        super.onAttachView(view)
        view.setCurrentCountry(country)
        when {
            eventDetailMode.isCreateNew() -> view.setEditableMode()
            eventDetailMode.isEdit() -> view.setEditableMode()
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
        client.name = newClientName
        checkIsAllFieldsFilled()
    }

    override fun onClientPhoneChanged(newClientPhone: String) {
        client.phone = newClientPhone
        checkIsAllFieldsFilled()
    }

    override fun onClientEmailChanged(newClientEmail: String) {
        client.email = newClientEmail
        checkIsAllFieldsFilled()
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
            client.hasPhone()&&
            client.hasEmail()) {
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
                view?.showErrorSnackbar(error.localizedMessage)
            })
            .let(::addDisposable)
    }

    private fun editClient() {
        view?.showSaveProgress()
        clientInteractor.editClient(client)
            .doAfterTerminate {
                view?.hideSaveProgress()
            }
            .subscribe({ editedClient ->
                client = editedClient
                view?.close(client)
            }, { error ->
                view?.showErrorSnackbar(error.localizedMessage)
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