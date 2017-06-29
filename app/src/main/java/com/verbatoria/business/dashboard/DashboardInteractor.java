package com.verbatoria.business.dashboard;

import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.business.dashboard.processor.VerbatologProcessor;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.response.VerbatologEventResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;
import com.verbatoria.data.repositories.dashboard.IDashboardRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import java.util.List;
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
    public Observable<VerbatologModel> getVerbatologInfo(VerbatologModel verbatolog) {
        return mDashboardRepository.getVerbatologInfo(getAccessToken()).map(item -> VerbatologProcessor.convertInfoResponseToVerbatologModel(verbatolog, item));
    }

    @Override
    public Observable<VerbatologModel> getVerbatologEvents(VerbatologModel verbatolog) {
        return mDashboardRepository.getVerbatologEvents(getAccessToken()).map(item -> VerbatologProcessor.convertEventsResponseToVerbatologModel(verbatolog, item));
    }

    private String getAccessToken() {
        TokenModel tokenModel = mTokenRepository.getToken();
        return tokenModel.getAccessToken();
    }

}
