package com.verbatoria.data.repositories.dashboard;

import com.verbatoria.business.dashboard.models.LocationModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.data.network.request.LocationLanguageRequestModel;
import com.verbatoria.data.network.response.LocationResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Completable;
import rx.Observable;

/**
 *
 * Интерфейс-репозитория для dashboard
 *
 * @author nikitaremnev
 */
public interface IDashboardRepository {

    Observable<VerbatologInfoResponseModel> getVerbatologInfo(String accessToken);

    Observable<LocationResponseModel> getLocation(String accessToken);

    Observable<ResponseBody> getCountries(String accessToken);

    Observable<VerbatologModel> getVerbatologInfoFromCache();

    Observable<LocationModel> getLocationFromCache();

    LocationModel getLocationSyncFromCache();

    Completable updateCurrentLocale(String accessToken, String locationId, LocationLanguageRequestModel locationLanguageRequestModel);

    void saveVerbatologInfo(VerbatologModel verbatologModel);

    void saveLocationInfo(LocationModel locationModel);

    String getLocationId();

    List<String> getAvailableLanguages();

    String getCurrentLocale();

    void saveCurrentLocale(String newCurrentLocale);

    boolean isShowSettings();

    void setShowSettings(boolean showSettings);

}
