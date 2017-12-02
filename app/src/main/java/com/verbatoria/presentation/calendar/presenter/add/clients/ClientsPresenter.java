package com.verbatoria.presentation.calendar.presenter.add.clients;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;

import com.verbatoria.business.clients.IClientsInteractor;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.view.add.clients.IClientsView;
import com.verbatoria.presentation.calendar.view.add.clients.search.ISearchClientsView;

import java.util.List;

import static com.verbatoria.presentation.calendar.view.add.clients.ClientsActivity.EXTRA_CLIENT_MODEL;

/**
 * Реализация презентера для экрана данных о клиенте
 *
 * @author nikitaremnev
 */
public class ClientsPresenter extends BasePresenter implements IClientsPresenter {


    private IClientsInteractor mClientsInteractor;
    private IClientsView mClientView;
    private ISearchClientsView mSearchClientsView;
    private ClientModel mClientModel;
    private boolean mIsEditMode;
//    private boolean mSearchByPhone;

    public ClientsPresenter(IClientsInteractor clientsInteractor) {
        mClientsInteractor = clientsInteractor;
    }

    @Override
    public void bindView(@NonNull ISearchClientsView searchClientsView) {
        mSearchClientsView = searchClientsView;
    }

    @Override
    public void bindView(@NonNull IClientsView clientView) {
        mClientView = clientView;
    }

    @Override
    public void unbindView() {
        mClientView = null;
        mSearchClientsView = null;
    }

    @Override
    public void obtainClient(Intent intent) {
        mClientModel = intent.getParcelableExtra(EXTRA_CLIENT_MODEL);
        if (mClientModel != null) {
            mIsEditMode = true;
        }
    }

    @Override
    public ClientModel getClientModel() {
        return mClientModel;
    }

    @Override
    public boolean isEditMode() {
        return mIsEditMode;
    }

    @Override
    public void createClient() {
        if (!isEmailCorrect(mClientView.getClientEmail())) {
            return;
        }
        mClientModel = new ClientModel();
        mClientModel.setName(mClientView.getClientName());
        mClientModel.setEmail(mClientView.getClientEmail());
        mClientModel.setPhone(mClientView.getClientPhone());
        addSubscription(mClientsInteractor.addClient(mClientModel)
                .doOnSubscribe(() -> mClientView.showProgress())
                .doOnUnsubscribe(() -> mClientView.hideProgress())
                .subscribe(this::handleClientAddSuccess, this::handleClientRequestError));
    }

    @Override
    public void editClient() {
        if (!isEmailCorrect(mClientView.getClientEmail())) {
            return;
        }
        mClientModel.setName(mClientView.getClientName());
        mClientModel.setEmail(mClientView.getClientEmail());
        mClientModel.setPhone(mClientView.getClientPhone());
        addSubscription(mClientsInteractor.editClient(mClientModel)
                .doOnSubscribe(() -> mClientView.showProgress())
                .doOnUnsubscribe(() -> mClientView.hideProgress())
                .subscribe(this::handleClientEditSuccess, this::handleClientRequestError));
    }

    @Override
    public void searchClients() {
        addSubscription(mClientsInteractor.searchClients(mSearchClientsView.getQuery())
                .doOnSubscribe(() -> mSearchClientsView.showProgress())
                .doOnUnsubscribe(() -> mSearchClientsView.hideProgress())
                .subscribe(this::handleClientsFound, this::handleClientsSearchError));
    }

    @Override
    public String getClientName() {
        return mClientModel != null ? mClientModel.getName() != null ? mClientModel.getName() : "" : "";
    }

    @Override
    public String getClientPhone() {
        return mClientModel != null ? mClientModel.getPhone() != null ? mClientModel.getPhone() : "" : "";
    }


    @Override
    public String getClientEmail() {
        return mClientModel != null ? mClientModel.getEmail() != null ? mClientModel.getEmail() : "" : "";
    }

    @Override
    public String getCountry() {
        return mClientsInteractor.getCountry();
    }

    @Override
    public void saveState(Bundle outState) {

    }

    @Override
    public void restoreState(Bundle savedInstanceState) {

    }

    private void handleClientAddSuccess(ClientModel clientModel) {
        mClientModel = clientModel;
        mClientView.showClientAdded();
    }

    private void handleClientEditSuccess(ClientModel clientModel) {
        mClientView.showClientEdited();
    }

    private void handleClientsFound(List<ClientModel> clients) {
        mSearchClientsView.showClientsFound(clients);
    }

    private void handleClientsSearchError(Throwable throwable) {
        mSearchClientsView.showError(throwable.getMessage());
    }

    private void handleClientRequestError(Throwable throwable) {
        mClientView.showError(throwable.getMessage());
    }

    private boolean isEmailCorrect(String email) {
        if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            mClientView.showEmailIncorrectError();
            return false;
        }
    }

}
