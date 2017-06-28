package com.verbatoria.data.repositories.dashboard;

import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.token.interactor.TokenInteractor;
import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;

import javax.inject.Inject;

import rx.Observable;

/**
 *
 * Реализация репозитория для dashboard
 *
 * @author nikitaremnev
 */
public class DashboardRepository implements IDashboardRepository {

    @Inject
    public TokenInteractor mTokenInteractor;

    public DashboardRepository() {
        VerbatoriaApplication.getApplicationComponent().inject(this);
    }

    @Override
    public Observable<VerbatologInfoResponseModel> getVerbatologInfo(String accessToken) {
        return APIFactory.getAPIService().verbatologInfoRequest(accessToken);
    }
}
