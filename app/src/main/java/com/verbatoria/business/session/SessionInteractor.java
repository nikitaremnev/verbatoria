package com.verbatoria.business.session;

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
public class SessionInteractor implements ISessionInteractor {

    private ISessionRepository mSessionRepository;
    private ITokenRepository mTokenRepository;

    public SessionInteractor(ISessionRepository sessionRepository, ITokenRepository tokenRepository) {
        mSessionRepository = sessionRepository;
        mTokenRepository = tokenRepository;
    }

    @Override
    public Observable<StartSessionResponseModel> startSession(String eventId) {
        return mSessionRepository.startSession(getAccessToken(), getStartSessionRequestModel(eventId));
    }

    private String getAccessToken() {
        TokenModel tokenModel = mTokenRepository.getToken();
        return tokenModel.getAccessToken();
    }

    private StartSessionRequestModel getStartSessionRequestModel(String eventId) {
        return new StartSessionRequestModel()
                .setEventId(eventId);
    }

}
