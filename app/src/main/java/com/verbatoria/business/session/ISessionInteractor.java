package com.verbatoria.business.session;

import com.neurosky.connection.EEGPower;
import com.verbatoria.data.network.response.StartSessionResponseModel;

import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Интерфейс интерактора для сессии
 *
 * @author nikitaremnev
 */
public interface ISessionInteractor {

    Observable<StartSessionResponseModel> startSession(String eventId);
    Completable finishSession(String eventId);

    Completable getAllMeasurements(Map<String, String> answers);
    Completable submitResults();
    Completable submitResults(String fileName);

    Observable<Boolean> hasMeasurements();
    Completable clearDatabases();

    Completable cleanUp();

    Completable sendReportToLocation(String reportId);

    void startConnection();

    void dropConnection();

    Completable includeAttentionMemory(String reportId);

    boolean isSchoolAccount();

    /*
        ЖЦ коллбеков
     */
    void setConnectionCallback(IConnectionCallback connectionCallback);
    void setDataReceivedCallback(IDataReceivedCallback dataReceivedCallback);
    void setActivitiesCallback(IActivitiesCallback activitiesTimerCallback);
    void setPlayerCallback(IPlayerCallback playerCallback);

    void dropCallbacks();

    void closeDatabases();

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
    boolean isActivityRunningNow();

    /*
        Работа с музыкальным плеером
     */
    void playClick();
    void pauseClick();
    void nextClick();
    void backClick();

    void saveSessionId(String sessionId);
    void saveAge(int age);

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

    interface ISessionCallback {
        void onConnectionStateChanged(int code);
        void onDataReceivedCallback(int code, int value);
        void onEEGDataReceivedCallback(EEGPower eegPower);
        void onBluetoothDisabled();
    }

}
