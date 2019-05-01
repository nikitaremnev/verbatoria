package com.verbatoria.presentation.session.presenter.submit.school;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.presentation.session.view.submit.school.ISchoolSubmitView;
import com.verbatoria.utils.Logger;

import java.util.HashMap;
import java.util.Map;
import static com.verbatoria.presentation.session.view.connection.ConnectionActivity.EXTRA_EVENT_MODEL;

/**
 * Реализация презентера для экрана отправки результатов
 *
 * @author nikitaremnev
 */

public class SchoolSubmitPresenter implements ISchoolSubmitPresenter {

    private static final String TAG = "SchoolSubmitPresenter";

    private ISessionInteractor sessionInteractor;
    private ISchoolSubmitView view;
    private EventModel eventModel;

    public SchoolSubmitPresenter(ISessionInteractor sessionInteractor) {
        this.sessionInteractor = sessionInteractor;
    }

    @Override
    public void bindView(@NonNull ISchoolSubmitView schoolSubmitView) {
        view = schoolSubmitView;
    }

    @Override
    public void unbindView() {
        view = null;
        sessionInteractor.dropCallbacks();
    }

    @Override
    public void sendResults() {
        view.showProgress();
        Map<String, String> answers = new HashMap<>();
        sessionInteractor.getAllMeasurements(answers)
                .subscribe(this::handleMeasurementsReceived, this::handleError);
    }

    @Override
    public void obtainEvent(Intent intent) {
        eventModel = intent.getParcelableExtra(EXTRA_EVENT_MODEL);
    }

    private void handleMeasurementsReceived() {
        sessionInteractor.submitResults()
                .subscribe(this::handleResultsSubmitted, this::handleError);
    }

    private void handleResultsSubmitted() {
        sessionInteractor.dropConnection();
        sessionInteractor.finishSession(eventModel.getId())
                .subscribe(this::handleSessionFinished, this::handleError);
    }

    private void handleSessionFinished() {
        sessionInteractor.cleanUp()
                .subscribe(this::cleanUpFinished, this::handleError);
    }

    private void cleanUpFinished() {
        view.hideProgress();
        view.finishSession();
    }

    private void handleReportBackUp() {
        view.hideProgress();
        view.finishSessionWithError();
    }

    private void handleReportBackUpError(Throwable throwable) {
        throwable.printStackTrace();
        Logger.exc(TAG, throwable.getLocalizedMessage(), throwable);
        view.hideProgress();
        view.showError(throwable.getLocalizedMessage());
    }

    private void handleError(Throwable throwable) {
        throwable.printStackTrace();
        Logger.exc(TAG, throwable.getLocalizedMessage(), throwable);
        view.showError(throwable.getLocalizedMessage());
        sessionInteractor.backupReport(eventModel)
                .subscribe(this::handleReportBackUp, this::handleReportBackUpError);
    }

}
