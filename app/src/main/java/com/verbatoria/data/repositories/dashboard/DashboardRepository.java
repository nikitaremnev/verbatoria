package com.verbatoria.data.repositories.dashboard;

import com.verbatoria.business.dashboard.models.AgeGroupModel;
import com.verbatoria.business.dashboard.models.LocationModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.request.LocationLanguageRequestModel;
import com.verbatoria.data.network.response.AgeGroupResponseModel;
import com.verbatoria.data.network.response.LocationResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;
import com.verbatoria.utils.PreferencesStorage;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Completable;
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
    public Observable<List<AgeGroupResponseModel>> getAgeGroupsForArchimed(String accessToken) {
        return APIFactory.getAPIService().getAgeGroupsForArchimed(accessToken);
    }

    @Override
    public Observable<List<AgeGroupModel>> getAgeGroupsFromCache() {
        return Observable.fromCallable(() -> PreferencesStorage.getInstance().getAgeGroups());
    }

    @Override
    public LocationModel getLocationSyncFromCache() {
        return PreferencesStorage.getInstance().getLocationInfo();
    }

    @Override
    public Completable updateCurrentLocale(String accessToken, String locationId, LocationLanguageRequestModel locationLanguageRequestModel) {
        return APIFactory.getAPIService().setLocationLanguage(accessToken, locationId, locationLanguageRequestModel);
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

    @Override
    public List<String> getAvailableLanguages() {
        return PreferencesStorage.getInstance().getLocationInfo().getAvailableLocales();
    }

    @Override
    public String getCurrentLocale() {
        return PreferencesStorage.getInstance().getCurrentLocale();
    }

    @Override
    public void saveCurrentLocale(String newCurrentLocale) {
        PreferencesStorage.getInstance().setCurrentLocale(newCurrentLocale);
    }

    @Override
    public void saveAgeGroups(List<AgeGroupModel> ageGroupModels) {
        PreferencesStorage.getInstance().setAgeGroups(ageGroupModels);
    }

    @Override
    public boolean isShowSettings() {
        return PreferencesStorage.getInstance().getShowSettings();
    }

    @Override
    public void setShowSettings(boolean showSettings) {
        PreferencesStorage.getInstance().setShowSettings(showSettings);
    }

}
