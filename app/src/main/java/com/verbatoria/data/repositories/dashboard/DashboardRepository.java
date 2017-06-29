package com.verbatoria.data.repositories.dashboard;

import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;
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
}
