package com.verbatoria.presentation.session.presenter;

import android.support.annotation.NonNull;
import com.neurosky.connection.ConnectionStates;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.presentation.session.view.connection.IConnectionView;

/**
 * Реализация презентера для экрана соединения
 *
 * @author nikitaremnev
 */
public class ConnectionPresenter implements IConnectionPresenter, ISessionInteractor.IConnectionCallback {

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
    public void connect() {
        mSessionInteractor.startConnection();
    }

    @Override
    public void onConnectionStateChanged(int connectionCode) {
        switch (connectionCode) {
            case ConnectionStates.STATE_CONNECTING:
                mConnectionView.showConnectingState();
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

}
