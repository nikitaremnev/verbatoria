package com.verbatoria.business.session;

import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.request.StartSessionRequestModel;
import com.verbatoria.data.network.response.StartSessionResponseModel;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;

import rx.Observable;

/**
 * Реализация интерактора для сессии
 *
 * @author nikitaremnev
 */
public class SessionInteractor implements ISessionInteractor, ISessionInteractor.IApplicationSessionInteractorCallback {

    private ISessionRepository mSessionRepository;
    private ITokenRepository mTokenRepository;
    private IConnectionCallback mConnectionCallback;
    private IDataReceivedCallback mDataReceivedCallback;

    public SessionInteractor(ISessionRepository sessionRepository, ITokenRepository tokenRepository) {
        mSessionRepository = sessionRepository;
        mTokenRepository = tokenRepository;
        VerbatoriaApplication.setSessionInteractorCallback(this);
    }

    /*
        Session interactor
     */

    @Override
    public Observable<StartSessionResponseModel> startSession(String eventId) {
        return mSessionRepository.startSession(getAccessToken(), getStartSessionRequestModel(eventId));
    }

    @Override
    public void startConnection() {
        VerbatoriaApplication.tryToConnect();
    }

    @Override
    public void setConnectionCallback(IConnectionCallback connectionCallback) {
        mConnectionCallback = connectionCallback;
    }

    @Override
    public void setDataReceivedCallback(IDataReceivedCallback dataReceivedCallback) {
        mDataReceivedCallback = dataReceivedCallback;
    }

    @Override
    public void dropCallbacks() {
        mConnectionCallback = null;
        mDataReceivedCallback = null;
    }

     /*
        Application callback methods
     */

    @Override
    public void onConnectionStateChanged(int connectionCode) {
        if (mConnectionCallback != null) {
            mConnectionCallback.onConnectionStateChanged(connectionCode);
        }
    }

    @Override
    public void onDataReceivedCallback(int dataTypeCode, int value) {
        if (mDataReceivedCallback != null) {
            mDataReceivedCallback.onDataReceivedCallback(dataTypeCode, value);
        }
    }

    @Override
    public void onBluetoothDisabled() {
        if (mConnectionCallback != null) {
            mConnectionCallback.onBluetoothDisabled();
        }
    }

    /*
        Helper methods
     */
    private String getAccessToken() {
        TokenModel tokenModel = mTokenRepository.getToken();
        return tokenModel.getAccessToken();
    }

    private StartSessionRequestModel getStartSessionRequestModel(String eventId) {
        return new StartSessionRequestModel()
                .setEventId(eventId);
    }
}
