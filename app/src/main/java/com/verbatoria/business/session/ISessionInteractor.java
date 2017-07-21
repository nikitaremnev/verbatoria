package com.verbatoria.business.session;

import com.verbatoria.data.network.response.StartSessionResponseModel;

import rx.Observable;

/**
 * Интерфейс интерактора для сессии
 *
 * @author nikitaremnev
 */
public interface ISessionInteractor {

    Observable<StartSessionResponseModel> startSession(String eventId);

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
    void clearDoneActivities();
    String getAllUndoneActivities();
    boolean addActivityToDoneArray(String activity, long time);
    boolean addActivityToDoneArray(String activity);
    boolean containsDoneActivity(String activity);
    boolean removeActivityFromDoneArray(String activity);
    long getDoneActivitiesTime();
    long getDoneActivityTimeByCode(String code);

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
        void onDataReceivedCallback(int dataTypeCode, int value);
    }

    interface IActivitiesCallback {
        void updateTimer(String timerString);
        long getDoneActivitiesTime();
    }

    interface IPlayerCallback {
        void setUpPlayMode();
        void setUpPauseMode();
        void showPlayingFileName(String fileName);
        void showPlayer();
        void hidePlayer();
        void showPlayerError(String error);
    }

    interface IApplicationSessionInteractorCallback {
        void onConnectionStateChanged(int code);
        void onDataReceivedCallback(int code, int value);
        void onBluetoothDisabled();
    }



}
