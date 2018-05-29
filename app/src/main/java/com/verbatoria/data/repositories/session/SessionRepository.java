package com.verbatoria.data.repositories.session;

import android.content.Context;

import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.late_send.models.LateReportModel;
import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.request.StartSessionRequestModel;
import com.verbatoria.data.network.response.FinishSessionResponseModel;
import com.verbatoria.data.network.response.StartSessionResponseModel;
import com.verbatoria.data.repositories.session.database.ActivitiesDatabase;
import com.verbatoria.data.repositories.session.database.LateReportsDatabase;
import com.verbatoria.data.repositories.session.database.NeurodataDatabase;
import com.verbatoria.data.repositories.session.model.AttentionMeasurement;
import com.verbatoria.data.repositories.session.model.BaseMeasurement;
import com.verbatoria.data.repositories.session.model.EEGMeasurement;
import com.verbatoria.data.repositories.session.model.EventMeasurement;
import com.verbatoria.data.repositories.session.model.MediationMeasurement;
import com.verbatoria.utils.PreferencesStorage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 *
 * Реализация репозитория для dashboard
 *
 * @author nikitaremnev
 */
public class SessionRepository implements ISessionRepository {

    @Inject
    public Context mContext;

    public SessionRepository() {
        VerbatoriaApplication.getApplicationComponent().inject(this);
    }

    @Override
    public Observable<StartSessionResponseModel> startSession(String accessToken, StartSessionRequestModel eventId) {
        return APIFactory.getAPIService().startSessionRequest(accessToken, eventId);
    }

    @Override
    public Observable<FinishSessionResponseModel> finishSession(String accessToken) {
        return APIFactory.getAPIService().finishSessionRequest(PreferencesStorage.getInstance().getCurrentSessionId(), accessToken);
    }

    @Override
    public Observable<ResponseBody> includeAttentionMemory(String reportId, String accessToken) {
        return APIFactory.getAPIService().includeAttentionMemoryRequest(reportId, accessToken);
    }

    @Override
    public Observable<List<AttentionMeasurement>> getAttentionMeasurements() {
        return Observable.fromCallable(() -> NeurodataDatabase.getAttentionValues(mContext));
    }

    @Override
    public Observable<List<MediationMeasurement>> getMediationMeasurements() {
        return Observable.fromCallable(() -> NeurodataDatabase.getMediationValues(mContext));
    }

    @Override
    public Observable<List<EEGMeasurement>> getEEGMeasurements() {
        return Observable.fromCallable(() -> NeurodataDatabase.getEEGValues(mContext));
    }

    @Override
    public Observable<List<EventMeasurement>> getEventMeasurements() {
        return Observable.fromCallable(() -> ActivitiesDatabase.getEvents(mContext));
    }

    @Override
    public Observable<List<BaseMeasurement>> getAllMeasurements() {
        return Observable.fromCallable(() -> {
            List<BaseMeasurement> measurements = new ArrayList<>();
            measurements.addAll(NeurodataDatabase.getAttentionValues(mContext));
            measurements.addAll(NeurodataDatabase.getMediationValues(mContext));
            measurements.addAll(NeurodataDatabase.getEEGValues(mContext));
            measurements.addAll(ActivitiesDatabase.getEvents(mContext));
            return measurements;
        });
    }

    @Override
    public Observable<ResponseBody> addResults(String accessToken, RequestBody requestBody) {
        return APIFactory.getAPIService().addResultsToSessionRequest(PreferencesStorage.getInstance().getCurrentSessionId(), accessToken, requestBody);
    }

    @Override
    public Observable<ResponseBody> addResults(String accessToken, String sessionId, RequestBody requestBody) {
        return APIFactory.getAPIService().addResultsToSessionRequest(sessionId, accessToken, requestBody);
    }

    @Override
    public Observable<ResponseBody> sendReportToLocation(String accessToken, String reportId) {
        return APIFactory.getAPIService().sendReportToLocation(accessToken, reportId);
    }

    @Override
    public Observable<Boolean> hasMeasurements() {
        return Observable.fromCallable(() -> !NeurodataDatabase.getAttentionValues(mContext).isEmpty() || !ActivitiesDatabase.getEvents(mContext).isEmpty());
    }

    @Override
    public void addEvent(String code) {
        ActivitiesDatabase.addEventToDatabase(mContext, code);
    }

    @Override
    public void addAttentionValue(int value) {
        NeurodataDatabase.addAttentionToDatabase(mContext, System.currentTimeMillis(), value);
    }

    @Override
    public void addMediationValue(int value) {
        NeurodataDatabase.addMediationToDatabase(mContext, System.currentTimeMillis(), value);
    }

    @Override
    public void addEEGValue(int delta, int theta, int lowAlpha, int highAlpha,
                            int lowBeta, int highBeta, int lowGamma, int midGamma) {
        NeurodataDatabase.addEEGToDatabase(mContext, System.currentTimeMillis(), delta, theta,
                lowAlpha, highAlpha, lowBeta, highBeta, lowGamma, midGamma);
    }

    @Override
    public void saveSessionId(String sessionId) {
        PreferencesStorage.getInstance().setCurrentSessionId(sessionId);
    }

    @Override
    public void cleanUp() {
        ActivitiesDatabase.clear(mContext);
        NeurodataDatabase.clear(mContext);
    }

    @Override
    public void backupReport(LateReportModel lateReportModel) {
        LateReportsDatabase.addReportBackup(mContext, lateReportModel);
    }
}
