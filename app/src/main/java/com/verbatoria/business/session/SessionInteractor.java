package com.verbatoria.business.session;

import com.neurosky.connection.DataType.MindDataType;
import com.neurosky.connection.EEGPower;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.session.manager.AudioPlayerManager;
import com.verbatoria.business.session.processor.AttentionValueProcessor;
import com.verbatoria.business.session.processor.DoneActivitiesProcessor;
import com.verbatoria.business.session.activities.ActivitiesTimerTask;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.business.token.processor.TokenProcessor;
import com.verbatoria.data.network.request.StartSessionRequestModel;
import com.verbatoria.data.network.response.StartSessionResponseModel;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.utils.DateUtils;
import com.verbatoria.utils.Logger;

import java.util.Timer;

import rx.Observable;

import static com.verbatoria.business.session.activities.ActivitiesCodes.NO_CODE;

/**
 * Реализация интерактора для сессии
 *
 * @author nikitaremnev
 */
public class SessionInteractor implements ISessionInteractor, ISessionInteractor.IApplicationSessionInteractorCallback {

    private static final String TAG = SessionInteractor.class.getSimpleName();

    private ISessionRepository mSessionRepository;
    private ITokenRepository mTokenRepository;
    private IConnectionCallback mConnectionCallback;
    private IDataReceivedCallback mDataReceivedCallback;
    private IActivitiesCallback mActivitiesCallback;
    private IPlayerCallback mPlayerCallback;

    private Timer mActivitiesTimer;
    private ActivitiesTimerTask mActivitiesTimerTask;

    private AudioPlayerManager mPlayerManager;

    private String mCurrentCode = NO_CODE;

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
        if (mPlayerManager != null) {
            mPlayerManager.dropPlayer();
        }
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
    public long getDoneActivitiesTime() {
        return DoneActivitiesProcessor.getSumOfTime();
    }

    @Override
    public long getDoneActivityTimeByCode(String code) {
        return DoneActivitiesProcessor.getSumOfTimeByCode(code);
    }

    @Override
    public void processCode(String code) {
        if (mCurrentCode == NO_CODE) { //начинаем активность
            //добавляем активности
            mSessionRepository.addEvent(code);
            addActivityToDoneArray(code);
            mCurrentCode = code;
            //TODO: possible export - false
            //запускаем таймеры для активностей
            mActivitiesTimerTask.setStartActivityTime(System.currentTimeMillis());
            mActivitiesTimerTask.setFullActivitySeconds(getDoneActivityTimeByCode(mCurrentCode));
            mActivitiesTimerTask.setActivityActive(true);
        } else if (mCurrentCode.equals(code)) { //заканчиваем активность
            mSessionRepository.addEvent(code);
            addActivityToDoneArray(code);
            addActivityToDoneArray(code, (System.currentTimeMillis() - mActivitiesTimerTask.getStartActivityTime()) / DateUtils.MILLIS_PER_SECOND);
            mCurrentCode = NO_CODE;
           //TODO: possible export - true
            //drop timer
            mActivitiesTimerTask.setStartActivityTime(0);
            mActivitiesTimerTask.setFullActivitySeconds(0);
            mActivitiesTimerTask.setCurrentActivitySeconds(0);
            mActivitiesTimerTask.setActivityActive(false);
        } else { //заканчиваем предыдущую активность и начинаем новую
            //заканчиваем
            mSessionRepository.addEvent(mCurrentCode);
            addActivityToDoneArray(mCurrentCode, (System.currentTimeMillis() - mActivitiesTimerTask.getStartActivityTime()) / DateUtils.MILLIS_PER_SECOND);
            //начинаем новую
            mSessionRepository.addEvent(code);
            addActivityToDoneArray(code);

            mCurrentCode = code;
            //TODO: possible export - false
            //start timer
            mActivitiesTimerTask.setStartActivityTime(System.currentTimeMillis());
            mActivitiesTimerTask.setFullActivitySeconds(getDoneActivityTimeByCode(mCurrentCode));
            mActivitiesTimerTask.setCurrentActivitySeconds(0);
            mActivitiesTimerTask.setActivityActive(true);
        }
        mActivitiesCallback.updateButtonsState(mCurrentCode);
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
            int formattedValue = value;
            switch (dataTypeCode) {
                case MindDataType.CODE_ATTENTION:
                    formattedValue = AttentionValueProcessor.processValue(value);
                    Logger.e(TAG, "attention: " + value);
                    mSessionRepository.addAttentionValue(formattedValue);
                    mDataReceivedCallback.onAttentionValueReceived(formattedValue);
                    break;
                case MindDataType.CODE_MEDITATION:
                    Logger.e(TAG, "mediation: " + value);
                    mSessionRepository.addMediationValue(formattedValue);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onEEGDataReceivedCallback(EEGPower eegPower) {
        if (mDataReceivedCallback != null) {
            mSessionRepository.addEEGValue(eegPower.delta, eegPower.theta, eegPower.lowAlpha, eegPower.highAlpha,
                    eegPower.lowBeta, eegPower.highBeta, eegPower.lowGamma, eegPower.middleGamma);
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
        mActivitiesTimerTask = new ActivitiesTimerTask(mActivitiesCallback);
        mActivitiesTimer.schedule(mActivitiesTimerTask, ActivitiesTimerTask.ACTIVITIES_TIMER_DELAY, ActivitiesTimerTask.ACTIVITIES_TIMER_DELAY);
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
