package com.verbatoria.presentation.session.presenter.writing;

import android.content.Context;
import android.support.annotation.NonNull;
import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.DataType.MindDataType;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.databases.StatisticsDatabase;
import com.remnev.verbatoriamini.util.NeuroExcelWriter;
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

    private String mSelectedButtonText = NO_CODE;

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
    public void showPlayer() {
        mWritingView.showPlayer();
    }

    @Override
    public void hidePlayer() {
        mWritingView.hidePlayer();
    }

    @Override
    public void submitCode(String code) {
        if (code.equals(MUSIC_BUTTON_CODE)) {
            mWritingView.showPlayer();
        } else {
            mWritingView.hidePlayer();
        }
        processCode(code);
    }

    @Override
    public void showPlayerError(String error) {
        mWritingView.showSnackBar(error);
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
    public void onDataReceivedCallback(int dataTypeCode, int value) {
        switch (dataTypeCode) {
            case MindDataType.CODE_ATTENTION:
                mWritingView.addGraphEntry(value);
                break;
        }
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

    private void processCode(String code) {
        if (mSelectedButtonText != NO_CODE && mSelectedButtonText.equals(code)) {
            StatisticsDatabase.addEventToDatabase(mContext, code, NeuroExcelWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
            mWritingView.showSnackBar(mContext.getString(R.string.session_success_write_event));
            mSelectedButtonText = NO_CODE;
            updateButtonsState(NO_CODE);

//            mSessionInteractor.addActivityToDoneArray(code);
//            mSessionInteractor.addActivityToDoneArray(code, (System.currentTimeMillis() - mStartActivityTime) / 1000);
//                changeExportValue(true);
//                mStartActivityTime = 0;
//                mFullLoadingNumberOfSeconds = 0;
//                mCurrentLoadingNumberOfSeconds = 0;
//                mIsSubtasksActive = false;
        } else {




//            String textToWrite = selectedButtonText;
//            StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, NeuroExcelWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
//            textToWrite = code;
//
//            mSessionInteractor.addActivityToDoneArray(textToWrite);
//            mSessionInteractor.addActivityToDoneArray(selectedButtonText, (System.currentTimeMillis() - mStartActivityTime) / 1000);
//
//            StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, NeuroExcelWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
//            Helper.showSnackBar(mLoadTextView, getString(R.string.success_write_event));
//            selectedButtonText = code;
            mSelectedButtonText = code;
            updateButtonsState(code);
//            changeExportValue(false);

//                mStartActivityTime = System.currentTimeMillis();
//                mFullLoadingNumberOfSeconds = NeuroApplicationClass.getDoneActivityTimeByCode(code);
//                mCurrentLoadingNumberOfSeconds = 0;
//                mIsSubtasksActive = true;
        }

//        if (code == NO_CODE) {
//            mSessionInteractor.addActivityToDoneArray(code);
//            StatisticsDatabase.addEventToDatabase(getActivity(), code, NeuroExcelWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
//            Helper.showSnackBar(mLoadTextView, getString(R.string.success_write_event));
//            selectedButtonText = code;
//            setAllButtonsUnselected(foundButtonByCode(code));
//            changeExportValue(false);
//
//            mStartActivityTime = System.currentTimeMillis();
//            mFullLoadingNumberOfSeconds = NeuroApplicationClass.getDoneActivityTimeByCode(code);
//            mIsSubtasksActive = true;
//        } else {
//            if (selectedButtonText.equals(code)) {
//                StatisticsDatabase.addEventToDatabase(getActivity(), code, NeuroExcelWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
//                Helper.showSnackBar(mLoadTextView, getString(R.string.success_write_event));
//                mLoadTextView.setText("");
//                selectedButtonText = "";
//                setAllButtonsUnselected(null);
//
//                NeuroApplicationClass.addActivityToDoneArray(code);
//                NeuroApplicationClass.addActivityToDoneArray(code, (System.currentTimeMillis() - mStartActivityTime) / 1000);
////                changeExportValue(true);
////                mStartActivityTime = 0;
////                mFullLoadingNumberOfSeconds = 0;
////                mCurrentLoadingNumberOfSeconds = 0;
////                mIsSubtasksActive = false;
//            } else {
//                String textToWrite = selectedButtonText;
//                StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, NeuroExcelWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
//                textToWrite = code;
//
//                NeuroApplicationClass.addActivityToDoneArray(textToWrite);
////                NeuroApplicationClass.addActivityToDoneArray(selectedButtonText, (System.currentTimeMillis() - mStartActivityTime) / 1000);
//
//                StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, NeuroExcelWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
//                Helper.showSnackBar(mLoadTextView, getString(R.string.success_write_event));
//                selectedButtonText = code;
//                setAllButtonsUnselected(foundButtonByCode(code));
//                changeExportValue(false);
//
////                mStartActivityTime = System.currentTimeMillis();
////                mFullLoadingNumberOfSeconds = NeuroApplicationClass.getDoneActivityTimeByCode(code);
////                mCurrentLoadingNumberOfSeconds = 0;
////                mIsSubtasksActive = true;
//            }
//        }
    }

    private void updateButtonsState(String selectedButtonCode) {
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
