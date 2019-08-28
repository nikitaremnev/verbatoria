package com.verbatoria.ui.session.presenter.connection;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.neurosky.connection.ConnectionStates;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.ui.session.view.connection.IConnectionView;
import com.verbatoria.utils.Logger;

/**
 * Реализация презентера для экрана соединения
 *
 * @author nikitaremnev
 */
public class ConnectionPresenter extends BasePresenter implements IConnectionPresenter, ISessionInteractor.IConnectionCallback {

    private static final String TAG = ConnectionPresenter.class.getSimpleName();

    private ISessionInteractor mSessionInteractor;
    private IConnectionView mConnectionView;
    public ConnectionPresenter(ISessionInteractor sessionInteractor) {
        this.mSessionInteractor = sessionInteractor;
    }

    @Override
    public void bindView(@NonNull IConnectionView connectionView) {
        mConnectionView = connectionView;
        mSessionInteractor.setConnectionCallback(this);
    }

    @Override
    public void unbindView() {
        mConnectionView = null;
        mSessionInteractor.dropCallbacks();
    }

    @Override
    public void startWriting() {
        mConnectionView.startWriting();
    }

    @Override
    public void connect() {
        mSessionInteractor.startConnection();
    }

    @Override
    public void onConnectionStateChanged(int connectionCode) {
        Logger.e(TAG, "connectionCode: " + connectionCode);
        switch (connectionCode) {
            case ConnectionStates.STATE_CONNECTING:
                mConnectionView.startLoading();
                break;
            case ConnectionStates.STATE_CONNECTED:
                mConnectionView.showConnectedState();
                break;
            case ConnectionStates.STATE_DISCONNECTED:
            case ConnectionStates.STATE_GET_DATA_TIME_OUT:
                mConnectionView.showDisconnectedState();
                break;
            case ConnectionStates.STATE_ERROR:
            case ConnectionStates.STATE_FAILED:
                mConnectionView.showErrorConnectionState();
                break;
        }
    }

    @Override
    public void onBluetoothDisabled() {
        mConnectionView.showBluetoothDisabled();
    }

    @Override
    public void saveState(Bundle outState) {

    }

    @Override
    public void restoreState(Bundle savedInstanceState) {

    }
}
