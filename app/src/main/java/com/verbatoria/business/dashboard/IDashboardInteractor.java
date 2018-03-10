package com.verbatoria.business.dashboard;

import com.verbatoria.business.dashboard.models.LocationModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;

import okhttp3.ResponseBody;
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

    Observable<ResponseBody> getCountries();

    Completable cleanUpDatabase();

    boolean hasLocationId();

    String getUserStatus();

    String getApplicationVersion();

    String getAndroidVersion();
}
