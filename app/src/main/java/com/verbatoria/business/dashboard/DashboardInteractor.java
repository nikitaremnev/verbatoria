package com.verbatoria.business.dashboard;

import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;
import com.verbatoria.data.repositories.dashboard.IDashboardRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.utils.Logger;

import rx.Observable;

/**
 * Реализация интерактора для dashboard
 *
 * @author nikitaremnev
 */
public class DashboardInteractor implements IDashboardInteractor {

    private static final String TAG = DashboardInteractor.class.getSimpleName();

    private IDashboardRepository mDashboardRepository;
    private ITokenRepository mTokenRepository;

    public DashboardInteractor(IDashboardRepository dashboardRepository, ITokenRepository tokenRepository) {
        mDashboardRepository = dashboardRepository;
        mTokenRepository = tokenRepository;
    }

    @Override
    public Observable<VerbatologInfoResponseModel> getVerbatologInfo() {
        TokenModel tokenModel = mTokenRepository.getToken();
        String accessToken = tokenModel.getAccessToken();
        Logger.e(TAG, "accessToken: " + accessToken);
        return mDashboardRepository.getVerbatologInfo(accessToken);
    }

}
