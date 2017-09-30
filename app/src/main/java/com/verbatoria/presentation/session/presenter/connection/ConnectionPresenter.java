package com.verbatoria.presentation.session.presenter.connection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.neurosky.connection.ConnectionStates;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.session.view.connection.ConnectionActivity;
import com.verbatoria.presentation.session.view.connection.IConnectionView;
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
    private EventModel mEventModel;

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
    public void obtainEvent(Intent intent) {
        mEventModel = intent.getParcelableExtra(ConnectionActivity.EXTRA_EVENT_MODEL);
    }

    @Override
    public EventModel getEvent() {
        return mEventModel;
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
