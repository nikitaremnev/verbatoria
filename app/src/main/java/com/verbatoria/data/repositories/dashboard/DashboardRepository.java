package com.verbatoria.data.repositories.dashboard;

import com.verbatoria.business.dashboard.models.LocationModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.response.LocationResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;
import com.verbatoria.utils.PreferencesStorage;

import okhttp3.ResponseBody;
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
    public Observable<LocationResponseModel> getLocation(String accessToken) {
        return APIFactory.getAPIService().getLocation(getLocationId(), accessToken);
    }

    @Override
    public Observable<ResponseBody> getCountries(String accessToken) {
        return APIFactory.getAPIService().getCountries(accessToken);
    }

    @Override
    public Observable<VerbatologModel> getVerbatologInfoFromCache() {
        return Observable.fromCallable(() -> PreferencesStorage.getInstance().getVerbatologInfo());
    }

    @Override
    public Observable<LocationModel> getLocationFromCache() {
        return Observable.fromCallable(() -> PreferencesStorage.getInstance().getLocationInfo());
    }

    @Override
    public void saveVerbatologInfo(VerbatologModel verbatologModel) {
        PreferencesStorage.getInstance().setVerbatologInfo(verbatologModel);
    }

    @Override
    public void saveLocationInfo(LocationModel locationModel) {
        PreferencesStorage.getInstance().setLocationInfo(locationModel);
    }

    @Override
    public String getLocationId() {
        return PreferencesStorage.getInstance().getLocationId();
    }
}
