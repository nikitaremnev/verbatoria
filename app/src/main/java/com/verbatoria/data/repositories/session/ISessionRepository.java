package com.verbatoria.data.repositories.session;

import com.verbatoria.data.network.request.StartSessionRequestModel;
import com.verbatoria.data.network.response.StartSessionResponseModel;
import com.verbatoria.data.repositories.session.model.AttentionMeasurement;
import com.verbatoria.data.repositories.session.model.BaseMeasurement;
import com.verbatoria.data.repositories.session.model.EEGMeasurement;
import com.verbatoria.data.repositories.session.model.EventMeasurement;
import com.verbatoria.data.repositories.session.model.MediationMeasurement;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 *
 * Интерфейс-репозитория для сессии
 *
 * @author nikitaremnev
 */
public interface ISessionRepository {

    Observable<StartSessionResponseModel> startSession(String accessToken, StartSessionRequestModel eventId);

    Observable<List<AttentionMeasurement>> getAttentionMeasurements();

    Observable<List<MediationMeasurement>> getMediationMeasurements();

    Observable<List<EEGMeasurement>> getEEGMeasurements();

    Observable<List<EventMeasurement>> getEventMeasurements();

    Observable<List<BaseMeasurement>> getAllMeasurements();

    Observable<ResponseBody> addResults(String accessToken, RequestBody requestBody);

    void addEvent(String code);

    void addAttentionValue(int value);

    void addMediationValue(int value);

    void addEEGValue(int delta, int theta, int lowAlpha, int highAlpha,
                     int lowBeta, int highBeta, int lowGamma, int midGamma);

    void saveSessionId(String sessionId);

    void cleanUp();

}
