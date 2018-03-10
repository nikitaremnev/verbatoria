package com.verbatoria.business.dashboard;

import android.text.TextUtils;

import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.LocationModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.business.dashboard.processor.ModelsConverter;
import com.verbatoria.business.session.processor.DoneActivitiesProcessor;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.repositories.dashboard.IDashboardRepository;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.utils.DeveloperUtils;
import com.verbatoria.utils.FileUtils;
import com.verbatoria.utils.PreferencesStorage;
import com.verbatoria.utils.RxSchedulers;

import java.io.File;

import okhttp3.ResponseBody;
import rx.Completable;
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
    private ISessionRepository mSessionRepository;

    public DashboardInteractor(IDashboardRepository dashboardRepository,
                               ITokenRepository tokenRepository,
                               ISessionRepository sessionRepository) {
        mDashboardRepository = dashboardRepository;
        mTokenRepository = tokenRepository;
        mSessionRepository = sessionRepository;
    }

    @Override
    public Observable<VerbatologModel> getVerbatologInfo(VerbatologModel verbatolog) {
        return mDashboardRepository.getVerbatologInfo(getAccessToken())
                .map(item -> ModelsConverter.convertInfoResponseToVerbatologModel(verbatolog, item))
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
    public Observable<LocationModel> getLocationInfoFromCache() {
        return mDashboardRepository.getLocationFromCache()
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Observable<LocationModel> getLocation() {
        return mDashboardRepository.getLocation(getAccessToken())
                .map(ModelsConverter::convertLocationResponseToLocationModel)
                .doOnNext(locationModel -> mDashboardRepository.saveLocationInfo(locationModel))
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Observable<ResponseBody> getCountries() {
        return mDashboardRepository.getCountries(getAccessToken())
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Completable cleanUpDatabase() {
        return Completable.fromCallable(() -> {
            mSessionRepository.cleanUp();
            DoneActivitiesProcessor.clearDoneActivities();
            DoneActivitiesProcessor.clearTimeDoneActivities();
            VerbatoriaApplication.dropActivitiesTimer();
            String fileName = PreferencesStorage.getInstance().getLastReportName();
            if (fileName != null) {
                File file = new File(FileUtils.getApplicationDirectory(), fileName);
                if (file.exists()) {
                    file.delete();
                }
            }
            return null;
        })
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public boolean hasLocationId() {
        return !TextUtils.isEmpty(mDashboardRepository.getLocationId());
    }

    @Override
    public String getUserStatus() {
        return mTokenRepository.getStatus();
    }

    @Override
    public String getApplicationVersion() {
        return DeveloperUtils.getApplicationVersion();
    }

    @Override
    public String getAndroidVersion() {
        return DeveloperUtils.getAndroidVersion();
    }

    private String getAccessToken() {
        TokenModel tokenModel = mTokenRepository.getToken();
        return tokenModel.getAccessToken();
    }

}
