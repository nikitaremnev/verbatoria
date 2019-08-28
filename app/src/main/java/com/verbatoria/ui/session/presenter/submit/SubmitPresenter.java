package com.verbatoria.ui.session.presenter.submit;

import android.support.annotation.NonNull;
import android.util.Log;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.ui.session.view.submit.ISubmitView;
import com.verbatoria.utils.Logger;

import java.util.Map;

import static com.verbatoria.ui.session.view.submit.questions.QuestionsAdapter.HOBBY_ANSWER_POSITION;

/**
 * Реализация презентера для экрана отправки результатов
 *
 * @author nikitaremnev
 */
public class SubmitPresenter implements ISubmitPresenter {

    private static final String TAG = "SubmitPresenter";

    private ISessionInteractor mSessionInteractor;
    private ISubmitView mSubmitView;

    private boolean isHobbyUpdateRequired;

    public SubmitPresenter(ISessionInteractor sessionInteractor) {
        this.mSessionInteractor = sessionInteractor;
    }

    @Override
    public void bindView(@NonNull ISubmitView submitView) {
        mSubmitView = submitView;
    }

    @Override
    public void unbindView() {
        mSubmitView = null;
        mSessionInteractor.dropCallbacks();
    }

    @Override
    public void sendResults(Map<String, String> answers) {
        mSubmitView.showProgress();

        String hobbyValue = answers.get(Integer.toString(HOBBY_ANSWER_POSITION));
//        if (hobbyValue != null && !mEventModel.getHobby() && hobbyValue.equals("1")) {
//            mEventModel.setHobby(true);
//            isHobbyUpdateRequired = true;
//        }

        mSessionInteractor.getAllMeasurements(answers)
                .subscribe(this::handleMeasurementsReceived, this::handleError);
    }

    private void handleMeasurementsReceived() {
        Log.e(TAG, "handleMeasurementsReceived");
        mSessionInteractor.submitResults()
                .subscribe(this::handleResultsSubmitted, this::handleError);
    }

    private void handleResultsSubmitted() {
        Log.e(TAG, "handleResultsSubmitted");
        mSessionInteractor.dropConnection();
//        if (isHobbyUpdateRequired) {
//            mSessionInteractor.updateHobbyValue(mEventModel)
//                    .subscribe(this::handleHobbyUpdatedReceived, this::handleError);
//        } else {
//            mSessionInteractor.finishSession(mEventModel.getId())
//                    .subscribe(this::handleSessionFinished, this::handleError);
//        }
    }

    private void handleHobbyUpdatedReceived() {
        Log.e(TAG, "handleHobbyUpdatedReceived");
//        mSessionInteractor.finishSession(mEventModel.getId())
//                .subscribe(this::handleSessionFinished, this::handleError);
    }

    private void handleSessionFinished() {
        Log.e(TAG, "handleSessionFinished");
        mSessionInteractor.cleanUp()
                .subscribe(this::cleanUpFinished, this::handleError);
    }

    private void cleanUpFinished() {
        Log.e(TAG, "cleanUpFinished");
        mSubmitView.hideProgress();
        mSubmitView.finishSession();
    }

    private void handleReportBackUp() {
        mSessionInteractor.dropConnection();
        mSessionInteractor.cleanUp()
                .subscribe(this::handleCleanupAfterBackupFinished, this::handleCleanupAfterBackupError);
    }

    private void handleReportBackUpError(Throwable throwable) {
        throwable.printStackTrace();
        Logger.exc(TAG, throwable.getLocalizedMessage(), throwable);
        mSubmitView.hideProgress();
        mSubmitView.showError(throwable.getLocalizedMessage());
    }

    private void handleError(Throwable throwable) {
        throwable.printStackTrace();
        Logger.exc(TAG, throwable.getLocalizedMessage(), throwable);
        mSubmitView.showError(throwable.getLocalizedMessage());
//        mSessionInteractor.backupReport(mEventModel)
//                .subscribe(this::handleReportBackUp, this::handleReportBackUpError);
    }

    private void handleCleanupAfterBackupFinished() {
        mSubmitView.hideProgress();
        mSubmitView.finishSessionWithError();
    }

    private void handleCleanupAfterBackupError(Throwable throwable) {
        Logger.exc(TAG, throwable.getLocalizedMessage(), throwable);
        mSubmitView.hideProgress();
        mSubmitView.finishSessionWithError();
    }

}
