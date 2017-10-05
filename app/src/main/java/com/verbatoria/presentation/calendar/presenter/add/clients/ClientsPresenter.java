package com.verbatoria.presentation.calendar.presenter.add.clients;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.verbatoria.business.clients.IClientsInteractor;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.data.network.response.MessageResponseModel;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.view.add.clients.IClientsView;

import static com.verbatoria.presentation.calendar.view.add.clients.ClientsActivity.EXTRA_CLIENT_MODEL;

/**
 * Реализация презентера для экрана данных о клиенте
 *
 * @author nikitaremnev
 */
public class ClientsPresenter extends BasePresenter implements IClientsPresenter {

    private IClientsInteractor mClientsInteractor;
    private IClientsView mClientView;
    private ClientModel mClientModel;
    private boolean mIsEditMode;

    public ClientsPresenter(IClientsInteractor clientsInteractor) {
        mClientsInteractor = clientsInteractor;
    }

    @Override
    public void bindView(@NonNull IClientsView clientView) {
        mClientView = clientView;
    }

    @Override
    public void unbindView() {
        mClientView = null;
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
        mClientsInteractor.addClient(getClientModel())
                .doOnSubscribe(() -> mClientView.showProgress())
                .doOnUnsubscribe(() -> mClientView.hideProgress())
                .subscribe(this::handleClientAdded, this::handleClientError);
    }

    @Override
    public void editClient() {
        mClientsInteractor.editClient(mClientModel)
                .doOnSubscribe(() -> mClientView.showProgress())
                .doOnUnsubscribe(() -> mClientView.hideProgress())
                .subscribe(this::handleClientAdded, this::handleClientError);
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
    public void saveState(Bundle outState) {

    }

    @Override
    public void restoreState(Bundle savedInstanceState) {

    }

    private void handleClientAdded(MessageResponseModel messageResponseModel) {
//        if (messageResponseModel.getMessage().equals(MessageResponseModel.CREATED_MESSAGE)) {
            mClientView.finishWithResult();
//        }
    }

    private void handleClientError(Throwable throwable) {
        mClientView.showError(throwable.getMessage());
    }
}
