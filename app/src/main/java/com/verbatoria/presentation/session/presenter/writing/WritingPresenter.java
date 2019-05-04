package com.verbatoria.presentation.session.presenter.writing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.neurosky.connection.ConnectionStates;
import com.remnev.verbatoria.R;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.presentation.session.view.writing.ActivityButtonState;
import com.verbatoria.presentation.session.view.writing.IWritingView;
import static com.verbatoria.business.session.activities.ActivitiesCodes.*;
import static com.verbatoria.presentation.session.view.connection.ConnectionActivity.EXTRA_EVENT_MODEL;

/**
 * Реализация презентера для экрана записи
 *
 * @author nikitaremnev
 */
public class WritingPresenter implements IWritingPresenter,
        ISessionInteractor.IConnectionCallback,
        ISessionInteractor.IDataReceivedCallback,
        ISessionInteractor.IActivitiesCallback,
        ISessionInteractor.IPlayerCallback {

    private static final String TAG = WritingPresenter.class.getSimpleName();

    private static final String MUSIC_BUTTON_CODE = "31";

    private ISessionInteractor sessionInteractor;
    private IWritingView view;
    private EventModel eventModel;

    public WritingPresenter(ISessionInteractor sessionInteractor) {
        this.sessionInteractor = sessionInteractor;
    }

    @Override
    public void bindView(@NonNull IWritingView writingView) {
        view = writingView;
        sessionInteractor.setConnectionCallback(this);
        sessionInteractor.setDataReceivedCallback(this);
        sessionInteractor.setPlayerCallback(this);
        sessionInteractor.setActivitiesCallback(this);
        updateButtonsState(NO_CODE);
    }

    @Override
    public void unbindView() {
        view = null;
        sessionInteractor.dropCallbacks();
    }

    @Override
    public void obtainEvent(Intent intent) {
        eventModel = intent.getParcelableExtra(EXTRA_EVENT_MODEL);
    }

    @Override
    public EventModel getEvent() {
        return eventModel;
    }

    @Override
    public void playClick() {
        sessionInteractor.playClick();
    }

    @Override
    public void pauseClick() {
        sessionInteractor.pauseClick();
    }

    @Override
    public void nextClick() {
        sessionInteractor.nextClick();
    }

    @Override
    public void backClick() {
        sessionInteractor.backClick();
    }

    @Override
    public void checkFinishAllowed() {
        boolean isActivityRunningNow = sessionInteractor.isActivityRunningNow();
        if (!isActivityRunningNow) {
            String allNotEnoughTimeActivities = sessionInteractor.getAllNotEnoughTimeActivities();
            if (TextUtils.isEmpty(allNotEnoughTimeActivities)) {
                if (sessionInteractor.isSchoolAccount()) {
                    view.finishSessionSchoolMode();
                } else {
                    view.finishSession();
                }
            } else {
                view.showSomeActivitiesNotFinished(allNotEnoughTimeActivities);
            }
        } else {
            view.showError(R.string.session_please_finish_load_error);
        }
    }

    @Override
    public void setUpPlayMode() {
        view.setUpPlayMode();
    }

    @Override
    public void setUpPauseMode() {
        view.setUpPauseMode();
    }

    @Override
    public void showPlayingFileName(String fileName) {
        view.showPlayingFileName(fileName);
    }

    @Override
    public void submitCode(String code) {
        if (code.equals(MUSIC_BUTTON_CODE)) {
            view.showPlayer();
        } else {
            view.hidePlayer();
        }
        sessionInteractor.processCode(code);
        if (TextUtils.isEmpty(sessionInteractor.getAllUndoneActivities())) {
            view.showFinishButton();
        }
    }

    @Override
    public void showPlayerError(String error) {
        view.showError(error);
    }

    @Override
    public void onConnectionStateChanged(int connectionCode) {
        switch (connectionCode) {
            case ConnectionStates.STATE_STOPPED:
            case ConnectionStates.STATE_DISCONNECTED:
            case ConnectionStates.STATE_GET_DATA_TIME_OUT:
            case ConnectionStates.STATE_ERROR:
            case ConnectionStates.STATE_FAILED:
                view.showConnectionError();
                break;
        }
    }

    @Override
    public void onAttentionValueReceived(int attentionValue) {
        view.addGraphEntry(attentionValue);
    }

    @Override
    public void onBluetoothDisabled() {

    }

    @Override
    public void updateTimer(String timerString) {
        view.updateTimer(timerString);
    }

    @Override
    public long getDoneActivitiesTime() {
        return sessionInteractor.getDoneActivitiesTime();
    }

    @Override
    public void updateButtonsState(String selectedButtonCode) {
        view.setButtonState(sessionInteractor.containsDoneActivity(CODE_99) ? ActivityButtonState.STATE_DONE : ActivityButtonState.STATE_NEW, CODE_99);
        view.setButtonState(sessionInteractor.containsDoneActivity(CODE_11) ? ActivityButtonState.STATE_DONE : ActivityButtonState.STATE_NEW, CODE_11);
        view.setButtonState(sessionInteractor.containsDoneActivity(CODE_21) ? ActivityButtonState.STATE_DONE : ActivityButtonState.STATE_NEW, CODE_21);
        view.setButtonState(sessionInteractor.containsDoneActivity(CODE_31) ? ActivityButtonState.STATE_DONE : ActivityButtonState.STATE_NEW, CODE_31);
        view.setButtonState(sessionInteractor.containsDoneActivity(CODE_41) ? ActivityButtonState.STATE_DONE : ActivityButtonState.STATE_NEW, CODE_41);
        view.setButtonState(sessionInteractor.containsDoneActivity(CODE_51) ? ActivityButtonState.STATE_DONE : ActivityButtonState.STATE_NEW, CODE_51);
        view.setButtonState(sessionInteractor.containsDoneActivity(CODE_61) ? ActivityButtonState.STATE_DONE : ActivityButtonState.STATE_NEW, CODE_61);
        view.setButtonState(sessionInteractor.containsDoneActivity(CODE_71) ? ActivityButtonState.STATE_DONE : ActivityButtonState.STATE_NEW, CODE_71);
        if (selectedButtonCode != null) {
            view.setButtonState(ActivityButtonState.STATE_SELECTED, selectedButtonCode);
        }
    }

}
