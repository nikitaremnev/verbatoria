package com.verbatoria.business.session;

import com.neurosky.connection.EEGPower;
import com.verbatoria.data.network.request.MeasurementRequestModel;
import com.verbatoria.data.network.response.StartSessionResponseModel;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Интерфейс интерактора для сессии
 *
 * @author nikitaremnev
 */
public interface ISessionInteractor {

    Observable<StartSessionResponseModel> startSession(String eventId);
    Observable<Void> getAllMeasurements(Map<String, String> answers);
    Observable<ResponseBody> submitResults(RequestBody requestBody);
    void cleanUp();

    void startConnection();

    /*
        ЖЦ коллбеков
     */
    void setConnectionCallback(IConnectionCallback connectionCallback);
    void setDataReceivedCallback(IDataReceivedCallback dataReceivedCallback);
    void setActivitiesCallback(IActivitiesCallback activitiesTimerCallback);
    void setPlayerCallback(IPlayerCallback playerCallback);

    void dropCallbacks();

    /*
        Activity методы
     */
    String getAllUndoneActivities();
    String getAllNotEnoughTimeActivities();
    void addActivityToDoneArray(String activity, long time);
    boolean addActivityToDoneArray(String activity);
    boolean containsDoneActivity(String activity);
    long getDoneActivitiesTime();
    long getDoneActivityTimeByCode(String code);
    void processCode(String code);

    /*
        Работа с музыкальным плеером
     */
    void playClick();
    void pauseClick();
    void nextClick();
    void backClick();

    void showPlayer();
    void hidePlayer();



    /*
        Коллбеки
     */
    interface IConnectionCallback {
        void onConnectionStateChanged(int connectionCode);
        void onBluetoothDisabled();
    }

    interface IDataReceivedCallback {
        void onAttentionValueReceived(int attentionValue);
    }

    interface IActivitiesCallback {
        void updateTimer(String timerString);
        long getDoneActivitiesTime();
        void updateButtonsState(String selectedButtonCode);
    }

    interface IPlayerCallback {
        void setUpPlayMode();
        void setUpPauseMode();
        void showPlayingFileName(String fileName);
        void showPlayerError(String error);
    }

    interface IApplicationSessionInteractorCallback {
        void onConnectionStateChanged(int code);
        void onDataReceivedCallback(int code, int value);
        void onEEGDataReceivedCallback(EEGPower eegPower);
        void onBluetoothDisabled();
    }

}
