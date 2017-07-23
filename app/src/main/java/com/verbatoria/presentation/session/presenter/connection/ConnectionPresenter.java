package com.verbatoria.presentation.session.presenter.connection;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.os.AsyncTaskCompat;

import com.neurosky.connection.ConnectionStates;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.data.network.response.StartSessionResponseModel;
import com.verbatoria.presentation.session.view.connection.ConnectionActivity;
import com.verbatoria.presentation.session.view.connection.IConnectionView;
import com.verbatoria.utils.Logger;
import com.verbatoria.utils.RxSchedulers;

/**
 * Реализация презентера для экрана соединения
 *
 * @author nikitaremnev
 */
public class ConnectionPresenter implements IConnectionPresenter, ISessionInteractor.IConnectionCallback {

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
    public void connect() {
        mSessionInteractor.startConnection();
    }

    @Override
    public void startSession() {
        if (mEventModel != null) {
            mSessionInteractor.startSession(mEventModel.getId())
                    .subscribeOn(RxSchedulers.getNewThreadScheduler())
                    .observeOn(RxSchedulers.getMainThreadScheduler())
                    .subscribe(this::handleSessionStarted, this::handleSessionStartError);
            mConnectionView.showProgress();
        } else {
            handleSessionStarted(null);
        }
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

    private void handleSessionStarted(@NonNull StartSessionResponseModel sessionResponseModel) {
        if (sessionResponseModel != null) {
            Logger.e(TAG, sessionResponseModel.toString());
        }
        mConnectionView.startWriting();
        mConnectionView.hideProgress();
    }

    private void handleSessionStartError(Throwable throwable) {
        throwable.printStackTrace();
        Logger.exc(TAG, throwable);
        mConnectionView.showError(throwable.getMessage());
        mConnectionView.hideProgress();
    }
}
