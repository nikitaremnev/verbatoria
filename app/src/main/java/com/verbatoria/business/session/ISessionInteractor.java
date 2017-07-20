package com.verbatoria.business.session;

import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.response.StartSessionResponseModel;

import rx.Observable;

/**
 * Интерфейс интерактора для сессии
 *
 * @author nikitaremnev
 */
public interface ISessionInteractor {

    Observable<StartSessionResponseModel> startSession(String eventId);

    void startConnection();

    void setConnectionCallback(IConnectionCallback connectionCallback);
    void setDataReceivedCallback(IDataReceivedCallback dataReceivedCallback);
    void dropCallbacks();

    interface IConnectionCallback {
        void onConnectionStateChanged(int connectionCode);
        void onBluetoothDisabled();
    }

    interface IDataReceivedCallback {
        void onDataReceivedCallback(int dataTypeCode, int value);
    }

    interface IApplicationSessionInteractorCallback {
        void onConnectionStateChanged(int code);
        void onDataReceivedCallback(int code, int value);
        void onBluetoothDisabled();
    }
}
