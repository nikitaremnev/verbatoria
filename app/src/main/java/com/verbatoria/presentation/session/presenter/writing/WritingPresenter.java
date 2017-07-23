package com.verbatoria.presentation.session.presenter.writing;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.neurosky.connection.ConnectionStates;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.presentation.session.view.writing.ActivityButtonState;
import com.verbatoria.presentation.session.view.writing.IWritingView;

import javax.inject.Inject;
import static com.verbatoria.business.session.activities.ActivitiesCodes.*;

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

    @Inject
    public Context mContext;

    private ISessionInteractor mSessionInteractor;
    private IWritingView mWritingView;

    public WritingPresenter(ISessionInteractor sessionInteractor) {
        this.mSessionInteractor = sessionInteractor;
    }

    @Override
    public void bindView(@NonNull IWritingView writingView) {
        mWritingView = writingView;
        mSessionInteractor.setConnectionCallback(this);
        mSessionInteractor.setDataReceivedCallback(this);
        mSessionInteractor.setPlayerCallback(this);
        mSessionInteractor.setActivitiesCallback(this);
        updateButtonsState(NO_CODE);
    }

    @Override
    public void unbindView() {
        mWritingView = null;
        mSessionInteractor.dropCallbacks();
    }

    @Override
    public void playClick() {
        mSessionInteractor.playClick();
    }

    @Override
    public void pauseClick() {
        mSessionInteractor.pauseClick();
    }

    @Override
    public void nextClick() {
        mSessionInteractor.nextClick();
    }

    @Override
    public void backClick() {
        mSessionInteractor.backClick();
    }

    @Override
    public void showPlayer() {
        mSessionInteractor.showPlayer();
    }

    @Override
    public void hidePlayer() {
        mSessionInteractor.hidePlayer();
    }

    @Override
    public void checkFinishAllowed() {
        String allNotEnoughTimeActivities = mSessionInteractor.getAllNotEnoughTimeActivities();
        if (TextUtils.isEmpty(allNotEnoughTimeActivities)) {
            mWritingView.finishSession();
        } else {
            mWritingView.finishSession();
//            mWritingView.showSomeActivitiesNotFinished(allNotEnoughTimeActivities);
        }
    }

    @Override
    public void setUpPlayMode() {
        mWritingView.setUpPlayMode();
    }

    @Override
    public void setUpPauseMode() {
        mWritingView.setUpPauseMode();
    }

    @Override
    public void showPlayingFileName(String fileName) {
        mWritingView.showPlayingFileName(fileName);
    }

    @Override
    public void submitCode(String code) {
        if (code.equals(MUSIC_BUTTON_CODE)) {
            mWritingView.showPlayer();
        } else {
            mWritingView.hidePlayer();
        }
        mSessionInteractor.processCode(code);
        if (TextUtils.isEmpty(mSessionInteractor.getAllUndoneActivities())) {
            mWritingView.showFinishButton();
        }
    }

    @Override
    public void showPlayerError(String error) {
        mWritingView.showError(error);
    }

    @Override
    public void onConnectionStateChanged(int connectionCode) {
        switch (connectionCode) {
            case ConnectionStates.STATE_DISCONNECTED:
            case ConnectionStates.STATE_GET_DATA_TIME_OUT:
            case ConnectionStates.STATE_ERROR:
            case ConnectionStates.STATE_FAILED:
                //TODO: return to connecting screen
                break;
        }
    }

    @Override
    public void onAttentionValueReceived(int attentionValue) {
        mWritingView.addGraphEntry(attentionValue);
    }

    @Override
    public void onBluetoothDisabled() {

    }

    @Override
    public void updateTimer(String timerString) {
        mWritingView.updateTimer(timerString);
    }

    @Override
    public long getDoneActivitiesTime() {
        return mSessionInteractor.getDoneActivitiesTime();
    }

    @Override
    public void updateButtonsState(String selectedButtonCode) {
        mWritingView.setButtonState(mSessionInteractor.containsDoneActivity(CODE_99) ? ActivityButtonState.STATE_DONE : ActivityButtonState.STATE_NEW, CODE_99);
        mWritingView.setButtonState(mSessionInteractor.containsDoneActivity(CODE_11) ? ActivityButtonState.STATE_DONE : ActivityButtonState.STATE_NEW, CODE_11);
        mWritingView.setButtonState(mSessionInteractor.containsDoneActivity(CODE_21) ? ActivityButtonState.STATE_DONE : ActivityButtonState.STATE_NEW, CODE_21);
        mWritingView.setButtonState(mSessionInteractor.containsDoneActivity(CODE_31) ? ActivityButtonState.STATE_DONE : ActivityButtonState.STATE_NEW, CODE_31);
        mWritingView.setButtonState(mSessionInteractor.containsDoneActivity(CODE_41) ? ActivityButtonState.STATE_DONE : ActivityButtonState.STATE_NEW, CODE_41);
        mWritingView.setButtonState(mSessionInteractor.containsDoneActivity(CODE_51) ? ActivityButtonState.STATE_DONE : ActivityButtonState.STATE_NEW, CODE_51);
        mWritingView.setButtonState(mSessionInteractor.containsDoneActivity(CODE_61) ? ActivityButtonState.STATE_DONE : ActivityButtonState.STATE_NEW, CODE_61);
        mWritingView.setButtonState(mSessionInteractor.containsDoneActivity(CODE_71) ? ActivityButtonState.STATE_DONE : ActivityButtonState.STATE_NEW, CODE_71);
        if (selectedButtonCode != null) {
            mWritingView.setButtonState(ActivityButtonState.STATE_SELECTED, selectedButtonCode);
        }
    }

}
