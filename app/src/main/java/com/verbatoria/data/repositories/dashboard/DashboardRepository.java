package com.verbatoria.data.repositories.dashboard;

import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.response.EventsResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;
import com.verbatoria.utils.PreferencesStorage;

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
        return APIFactory.getAPIService().getVerbatologInfoRequest(accessToken);
    }

    @Override
    public Observable<EventsResponseModel> getEvents(String accessToken) {
        return APIFactory.getAPIService().getEventsRequest(accessToken);
    }

    @Override
    public Observable<VerbatologModel> getVerbatologInfoFromCache() {
        return Observable.fromCallable(() -> PreferencesStorage.getInstance().getVerbatologInfo());
    }

    @Override
    public void saveVerbatologInfo(VerbatologModel verbatologModel) {
        PreferencesStorage.getInstance().setVerbatologInfo(verbatologModel);
    }
}
