package com.verbatoria.business.dashboard;

import com.verbatoria.business.dashboard.models.AgeGroupModel;
import com.verbatoria.business.dashboard.models.LocationModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;

import java.util.List;

import rx.Completable;
import rx.Observable;

/**
 * Интерфейс интерактора для dashboard
 *
 * @author nikitaremnev
 */
public interface IDashboardInteractor {

    Observable<VerbatologModel> getVerbatologInfo(VerbatologModel verbatolog);

    Observable<VerbatologModel> getVerbatologInfoFromCache();

    Observable<LocationModel> getLocationInfoFromCache();

    Observable<LocationModel> getLocation();

    Completable loadAgeGroups();

    Observable<List<AgeGroupModel>> getAgeGroups();

    Completable updateCurrentLocale(String locationId, String currentLocale);

    Completable cleanUpDatabase();

    String getUserStatus();

    String getApplicationVersion();

    String getAndroidVersion();

    LocationModel getCurrentLocation();

    boolean isShowSettings();

    void setShowSettings(boolean showSettings);

}
