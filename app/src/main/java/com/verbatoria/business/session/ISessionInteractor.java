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

}
