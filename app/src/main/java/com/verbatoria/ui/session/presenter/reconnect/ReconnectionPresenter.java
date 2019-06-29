package com.verbatoria.ui.session.presenter.reconnect;

import android.content.Intent;
import android.support.annotation.NonNull;
import com.neurosky.connection.ConnectionStates;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.ui.session.view.reconnect.IReconnectionView;
import com.verbatoria.utils.Logger;

import static com.verbatoria.ui.session.view.connection.ConnectionActivity.EXTRA_EVENT_MODEL;

/**
 * Реализация презентера для экрана соединения
 *
 * @author nikitaremnev
 */
public class ReconnectionPresenter implements IReconnectionPresenter, ISessionInteractor.IConnectionCallback {

    private static final String TAG = ReconnectionPresenter.class.getSimpleName();

    private ISessionInteractor mSessionInteractor;
    private IReconnectionView mReconnectionView;
    private EventModel mEventModel;

    public ReconnectionPresenter(ISessionInteractor sessionInteractor) {
        this.mSessionInteractor = sessionInteractor;
    }

    @Override
    public void bindView(@NonNull IReconnectionView connectionView) {
        mReconnectionView = connectionView;
        mSessionInteractor.setConnectionCallback(this);
    }

    @Override
    public void unbindView() {
        mReconnectionView = null;
        mSessionInteractor.dropCallbacks();
    }

    @Override
    public void obtainEvent(Intent intent) {
        mEventModel = intent.getParcelableExtra(EXTRA_EVENT_MODEL);
    }

    @Override
    public EventModel getEvent() {
        return mEventModel;
    }

    @Override
    public void continueSession() {
        mReconnectionView.continueWriting();
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
                mReconnectionView.startLoading();
                break;
            case ConnectionStates.STATE_CONNECTED:
                mReconnectionView.showConnectedState();
                break;
            case ConnectionStates.STATE_DISCONNECTED:
            case ConnectionStates.STATE_GET_DATA_TIME_OUT:
                mReconnectionView.showDisconnectedState();
                break;
            case ConnectionStates.STATE_ERROR:
            case ConnectionStates.STATE_FAILED:
                mReconnectionView.showErrorConnectionState();
                break;
        }
    }

    @Override
    public void onBluetoothDisabled() {
        mReconnectionView.showBluetoothDisabled();
    }

}
