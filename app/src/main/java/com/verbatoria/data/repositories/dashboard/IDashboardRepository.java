package com.verbatoria.data.repositories.dashboard;

import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.data.network.response.LocationResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;

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

    Observable<VerbatologModel> getVerbatologInfoFromCache();

    void saveVerbatologInfo(VerbatologModel verbatologModel);

    String getLocationId();
}
