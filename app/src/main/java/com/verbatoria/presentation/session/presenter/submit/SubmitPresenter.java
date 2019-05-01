package com.verbatoria.presentation.session.presenter.submit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.presentation.session.view.submit.ISubmitView;
import com.verbatoria.utils.Logger;

import java.util.Map;

import javax.inject.Inject;

import static com.verbatoria.presentation.session.view.connection.ConnectionActivity.EXTRA_EVENT_MODEL;
import static com.verbatoria.presentation.session.view.submit.questions.QuestionsAdapter.HOBBY_ANSWER_POSITION;

/**
 * Реализация презентера для экрана отправки результатов
 *
 * @author nikitaremnev
 */
public class SubmitPresenter implements ISubmitPresenter {

    private static final String TAG = "SubmitPresenter";

    private ISessionInteractor mSessionInteractor;
    private ISubmitView mSubmitView;
    private EventModel mEventModel;

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
        if (hobbyValue != null && !mEventModel.getHobby() && hobbyValue.equals("1")) {
            mEventModel.setHobby(true);
            isHobbyUpdateRequired = true;
        }

        mSessionInteractor.getAllMeasurements(answers)
                .subscribe(this::handleMeasurementsReceived, this::handleError);
    }

    @Override
    public void obtainEvent(Intent intent) {
        mEventModel = intent.getParcelableExtra(EXTRA_EVENT_MODEL);
    }

    private void handleMeasurementsReceived() {
        Log.e(TAG, "handleMeasurementsReceived");
        mSessionInteractor.submitResults()
                .subscribe(this::handleResultsSubmitted, this::handleError);
    }

    private void handleResultsSubmitted() {
        Log.e(TAG, "handleResultsSubmitted");
        mSessionInteractor.dropConnection();
        if (isHobbyUpdateRequired) {
            mSessionInteractor.updateHobbyValue(mEventModel)
                    .subscribe(this::handleHobbyUpdatedReceived, this::handleError);
        } else {
            mSessionInteractor.finishSession(mEventModel.getId())
                    .subscribe(this::handleSessionFinished, this::handleError);
        }
    }

    private void handleHobbyUpdatedReceived() {
        Log.e(TAG, "handleHobbyUpdatedReceived");
        mSessionInteractor.finishSession(mEventModel.getId())
                .subscribe(this::handleSessionFinished, this::handleError);
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
        mSubmitView.hideProgress();
        mSubmitView.finishSessionWithError();
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
        mSessionInteractor.backupReport(mEventModel)
                .subscribe(this::handleReportBackUp, this::handleReportBackUpError);
    }

}
