package com.verbatoria.business.dashboard;

import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.AgeGroupModel;
import com.verbatoria.business.dashboard.models.LocationModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.business.dashboard.processor.ModelsConverter;
import com.verbatoria.business.session.processor.DoneActivitiesProcessor;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.request.LocationLanguageRequestModel;
import com.verbatoria.data.repositories.dashboard.IDashboardRepository;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.utils.DeveloperUtils;
import com.verbatoria.utils.FileUtils;
import com.verbatoria.utils.PreferencesStorage;
import com.verbatoria.utils.RxSchedulers;
import java.io.File;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Observable;

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
                .doOnNext(verbatologModel -> mDashboardRepository.saveArchimedAllowedForVerbatolog(verbatologModel.getIsArchimedAllowed()))
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
                .doOnNext(locationModel -> {
                    String currentLocale = mDashboardRepository.getCurrentLocale();

                    mSessionRepository.saveIsSchoolAccount(locationModel.isSchool());

                    if (locationModel.getLocale().equals("zh-CN")) {
                        locationModel.setLocale("en");
                    }

                    if (currentLocale == null || !currentLocale.equals(locationModel.getLocale())) {
                        mDashboardRepository.saveCurrentLocale(locationModel.getLocale());
                        locationModel.setUpdateLocaleRequired(true);
                    }
                })
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Completable loadAgeGroups() {
        return Completable.fromObservable(
                mDashboardRepository.getAgeGroupsForArchimed(getAccessToken())
                        .map(ModelsConverter::convertAgeGroupsResponseModelToAgeGroupModels)
                        .doOnNext(ageGroups -> {
                            mDashboardRepository.saveAgeGroups(ageGroups);
                            }
                        )
        )
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public List<AgeGroupModel> getAgeGroupsFromCache() {
        return mDashboardRepository.getAgeGroupsFromCache();
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
    public Completable updateCurrentLocale(String locationId, String currentLocale) {
        return mDashboardRepository.updateCurrentLocale(getAccessToken(), locationId, createLocationLanguageRequestModel(currentLocale))
                .doOnTerminate(() -> mDashboardRepository.saveCurrentLocale(currentLocale))
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
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

    @Override
    public LocationModel getCurrentLocation() {
        return mDashboardRepository.getLocationSyncFromCache();
    }

    @Override
    public boolean isShowSettings() {
        return mDashboardRepository.isShowSettings();
    }

    @Override
    public boolean isArchimedAllowedForVerbatolog() {
        return mDashboardRepository.isArchimedAllowedForVerbatolog();
    }

    @Override
    public void setShowSettings(boolean showSettings) {
        mDashboardRepository.setShowSettings(showSettings);
    }

    private String getAccessToken() {
        TokenModel tokenModel = mTokenRepository.getToken();
        return tokenModel.getAccessToken();
    }

    private LocationLanguageRequestModel createLocationLanguageRequestModel(String locale) {
        return new LocationLanguageRequestModel().setLocale(locale);
    }

}
