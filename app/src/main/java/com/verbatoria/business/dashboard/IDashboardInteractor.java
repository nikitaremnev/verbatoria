package com.verbatoria.business.dashboard;

import com.verbatoria.business.dashboard.models.LocationModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;

import rx.Observable;

/**
 * Интерфейс интерактора для dashboard
 *
 * @author nikitaremnev
 */
public interface IDashboardInteractor {

    Observable<VerbatologModel> getVerbatologInfo(VerbatologModel verbatolog);

    Observable<VerbatologModel> getVerbatologInfoFromCache();

    Observable<LocationModel> getLocation();

    boolean hasLocationId();

    String getUserStatus();

    String getApplicationVersion();

    String getAndroidVersion();
}
