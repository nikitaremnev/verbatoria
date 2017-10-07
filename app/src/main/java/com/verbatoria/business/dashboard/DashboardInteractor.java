package com.verbatoria.business.dashboard;

import android.text.TextUtils;

import com.verbatoria.business.dashboard.models.LocationModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.business.dashboard.processor.VerbatologProcessor;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.repositories.dashboard.IDashboardRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.utils.RxSchedulers;

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
        return mDashboardRepository.getVerbatologInfo(getAccessToken())
                .map(item -> VerbatologProcessor.convertInfoResponseToVerbatologModel(verbatolog, item))
                .doOnNext(verbatologModel -> mDashboardRepository.saveVerbatologInfo(verbatologModel))
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Observable<VerbatologModel> getVerbatologInfoFromCache() {
        return mDashboardRepository.getVerbatologInfoFromCache()
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Observable<LocationModel> getLocation() {
        return mDashboardRepository.getLocation(getAccessToken())
                .map(VerbatologProcessor::convertLocationResponseToLocationModel)
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public boolean hasLocationId() {
        return !TextUtils.isEmpty(mDashboardRepository.getLocationId());
    }

    private String getAccessToken() {
        TokenModel tokenModel = mTokenRepository.getToken();
        return tokenModel.getAccessToken();
    }

}
