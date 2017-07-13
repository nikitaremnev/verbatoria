package com.verbatoria.data.repositories.session;

import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.request.StartSessionRequestModel;
import com.verbatoria.data.network.response.StartSessionResponseModel;
import com.verbatoria.data.network.response.VerbatologEventResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;
import com.verbatoria.data.repositories.dashboard.IDashboardRepository;

import java.util.List;

import rx.Observable;

/**
 *
 * Реализация репозитория для dashboard
 *
 * @author nikitaremnev
 */
public class SessionRepository implements ISessionRepository {

    public SessionRepository() {

    }

    @Override
    public Observable<StartSessionResponseModel> startSession(String accessToken, StartSessionRequestModel eventId) {
        return APIFactory.getAPIService().startSessionRequest(accessToken, eventId);
    }
}
