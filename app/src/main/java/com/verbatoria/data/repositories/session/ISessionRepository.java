package com.verbatoria.data.repositories.session;

import com.verbatoria.business.late_send.LateReportModel;
import com.verbatoria.data.network.request.StartSessionRequestModel;
import com.verbatoria.data.network.response.FinishSessionResponseModel;
import com.verbatoria.data.network.response.StartSessionResponseModel;
import com.verbatoria.data.repositories.session.model.BaseMeasurement;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Completable;
import rx.Observable;

/**
 *
 * Интерфейс-репозитория для сессии
 *
 * @author nikitaremnev
 */
public interface ISessionRepository {

    Observable<StartSessionResponseModel> startSession(String accessToken, StartSessionRequestModel eventId);

    Observable<FinishSessionResponseModel> finishSession(String accessToken);

    Completable includeAttentionMemory(String reportId, String accessToken);

    Observable<List<BaseMeasurement>> getAllMeasurements();

    Observable<ResponseBody> addResults(String accessToken, RequestBody requestBody);

    Completable sendReportToLocation(String accessToken, String reportId);

    Observable<Boolean> hasMeasurements();

    void addEvent(String code);

    void addAttentionValue(int value);

    void addMediationValue(int value);

    void addEEGValue(int delta, int theta, int lowAlpha, int highAlpha,
                     int lowBeta, int highBeta, int lowGamma, int midGamma);

    void saveSessionId(String sessionId);

    void saveCurrentAge(int age);

    void cleanUp();

    void closeDatabases();

    void backupReport(LateReportModel lateReportModel);

    void saveIsSchoolAccount(boolean isSchoolAccount);

    boolean isSchoolAccount();

}
