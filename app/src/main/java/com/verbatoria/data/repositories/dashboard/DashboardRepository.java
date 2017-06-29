package com.verbatoria.data.repositories.dashboard;

import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.response.VerbatologEventResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;

import java.util.List;

import rx.Observable;

/**
 *
 * Реализация репозитория для dashboard
 *
 * @author nikitaremnev
 */
public class DashboardRepository implements IDashboardRepository {


    public DashboardRepository() {

    }

    @Override
    public Observable<VerbatologInfoResponseModel> getVerbatologInfo(String accessToken) {
        return APIFactory.getAPIService().verbatologInfoRequest(accessToken);
    }

    @Override
    public Observable<List<VerbatologEventResponseModel>> getVerbatologEvents(String accessToken) {
        return APIFactory.getAPIService().verbatologEventsRequest(accessToken);
    }
}
