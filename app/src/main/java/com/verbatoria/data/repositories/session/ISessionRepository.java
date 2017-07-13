package com.verbatoria.data.repositories.session;

import com.verbatoria.data.network.request.StartSessionRequestModel;
import com.verbatoria.data.network.response.StartSessionResponseModel;

import rx.Observable;

/**
 *
 * Интерфейс-репозитория для сессии
 *
 * @author nikitaremnev
 */
public interface ISessionRepository {

    Observable<StartSessionResponseModel> startSession(String accessToken, StartSessionRequestModel eventId);

}
