package com.verbatoria.business.session;

import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.session.manager.AudioPlayerManager;
import com.verbatoria.business.session.processor.DoneActivitiesProcessor;
import com.verbatoria.business.session.timer.ActivitiesTimerTask;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.request.StartSessionRequestModel;
import com.verbatoria.data.network.response.StartSessionResponseModel;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;

import java.util.Timer;

import rx.Observable;

/**
 * Реализация интерактора для сессии
 *
 * @author nikitaremnev
 */
public class SessionInteractor implements ISessionInteractor, ISessionInteractor.IApplicationSessionInteractorCallback {

    private ISessionRepository mSessionRepository;
    private ITokenRepository mTokenRepository;
    private IConnectionCallback mConnectionCallback;
    private IDataReceivedCallback mDataReceivedCallback;
    private IActivitiesCallback mActivitiesCallback;
    private IPlayerCallback mPlayerCallback;

    private Timer mActivitiesTimer;

    private AudioPlayerManager mPlayerManager;

    public SessionInteractor(ISessionRepository sessionRepository, ITokenRepository tokenRepository) {
        mSessionRepository = sessionRepository;
        mTokenRepository = tokenRepository;
        VerbatoriaApplication.setSessionInteractorCallback(this);
    }

    /*
        Session interactor
     */

    @Override
    public Observable<StartSessionResponseModel> startSession(String eventId) {
        return mSessionRepository.startSession(getAccessToken(), getStartSessionRequestModel(eventId));
    }

    @Override
    public void startConnection() {
        VerbatoriaApplication.tryToConnect();
    }

    @Override
    public void setConnectionCallback(IConnectionCallback connectionCallback) {
        mConnectionCallback = connectionCallback;
    }

    @Override
    public void setDataReceivedCallback(IDataReceivedCallback dataReceivedCallback) {
        mDataReceivedCallback = dataReceivedCallback;
    }

    @Override
    public void setActivitiesCallback(IActivitiesCallback activitiesCallback) {
        mActivitiesCallback = activitiesCallback;
        startActivitiesTimer();
        initPlayers();
    }

    @Override
    public void setPlayerCallback(IPlayerCallback playerCallback) {
        mPlayerCallback = playerCallback;
    }

    @Override
    public void dropCallbacks() {
        mConnectionCallback = null;
        mDataReceivedCallback = null;
        mPlayerManager.dropPlayer();
        mPlayerManager = null;
        mPlayerCallback = null;
        dropActivitiesTimer();
    }

    /*
        Работа с процессором активностей
     */
    @Override
    public void clearDoneActivities() {
        DoneActivitiesProcessor.clearDoneActivities();
        DoneActivitiesProcessor.clearTimeDoneActivities();
    }

    @Override
    public String getAllUndoneActivities() {
        return DoneActivitiesProcessor.getAllUndoneActivities();
    }

    @Override
    public boolean addActivityToDoneArray(String activity, long time) {
        return DoneActivitiesProcessor.addActivityToDoneArray(activity, time);
    }

    @Override
    public boolean addActivityToDoneArray(String activity) {
        return DoneActivitiesProcessor.addActivityToDoneArray(activity);
    }

    @Override
    public boolean containsDoneActivity(String activity) {
        return DoneActivitiesProcessor.containsDoneActivity(activity);
    }

    @Override
    public boolean removeActivityFromDoneArray(String activity) {
        return DoneActivitiesProcessor.removeActivityFromDoneArray(activity);
    }

    @Override
    public long getDoneActivitiesTime() {
        return DoneActivitiesProcessor.getSumOfTime();
    }

    @Override
    public long getDoneActivityTimeByCode(String code) {
        return DoneActivitiesProcessor.getSumOfTimeByCode(code);
    }

    @Override
    public void playClick() {
        mPlayerManager.playClick();
    }

    @Override
    public void pauseClick() {
        mPlayerManager.pauseClick();
    }

    @Override
    public void nextClick() {
        mPlayerManager.nextClick();
    }

    @Override
    public void backClick() {
        mPlayerManager.backClick();
    }

    @Override
    public void showPlayer() {
        mPlayerManager.showPlayer();
    }

    @Override
    public void hidePlayer() {
        mPlayerManager.hidePlayer();
    }

     /*
        Application callback methods
     */

    @Override
    public void onConnectionStateChanged(int connectionCode) {
        if (mConnectionCallback != null) {
            mConnectionCallback.onConnectionStateChanged(connectionCode);
        }
    }

    @Override
    public void onDataReceivedCallback(int dataTypeCode, int value) {
        if (mDataReceivedCallback != null) {
            mDataReceivedCallback.onDataReceivedCallback(dataTypeCode, value);
        }
    }

    @Override
    public void onBluetoothDisabled() {
        if (mConnectionCallback != null) {
            mConnectionCallback.onBluetoothDisabled();
        }
    }

    /*
        Helper methods
     */
    private String getAccessToken() {
        TokenModel tokenModel = mTokenRepository.getToken();
        return tokenModel.getAccessToken();
    }

    private StartSessionRequestModel getStartSessionRequestModel(String eventId) {
        return new StartSessionRequestModel()
                .setEventId(eventId);
    }

    private void startActivitiesTimer() {
        mActivitiesTimer = new Timer();
        mActivitiesTimer.schedule(new ActivitiesTimerTask(mActivitiesCallback), ActivitiesTimerTask.ACTIVITIES_TIMER_DELAY, ActivitiesTimerTask.ACTIVITIES_TIMER_DELAY);
    }

    private void initPlayers() {
        mPlayerManager = new AudioPlayerManager(mPlayerCallback);
    }

    private void dropActivitiesTimer() {
        if (mActivitiesTimer != null) {
            mActivitiesTimer.cancel();
        }
        if (mActivitiesCallback != null) {
            mActivitiesCallback = null;
        }
    }

}
