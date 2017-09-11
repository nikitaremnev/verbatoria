package com.verbatoria.presentation.session.presenter.submit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.data.network.response.FinishSessionResponseModel;
import com.verbatoria.presentation.session.view.submit.ISubmitView;
import com.verbatoria.utils.Logger;
import java.util.Map;
import javax.inject.Inject;
import okhttp3.ResponseBody;

import static com.verbatoria.presentation.session.view.connection.ConnectionActivity.EXTRA_EVENT_MODEL;

/**
 * Реализация презентера для экрана отправки результатов
 *
 * @author nikitaremnev
 */
public class SubmitPresenter implements ISubmitPresenter {

    private static final String TAG = SubmitPresenter.class.getSimpleName();

    @Inject
    public Context mContext;

    private ISessionInteractor mSessionInteractor;
    private ISubmitView mSubmitView;
    private EventModel mEventModel;

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
        Log.e(TAG, "answer: " + answers.get("0"));
        Log.e(TAG, "answer: " + answers.get("1"));
        Log.e(TAG, "answer: " + answers.get("2"));
        Log.e(TAG, "answer: " + answers.get("3"));
        Log.e(TAG, "answer: " + answers.get("4"));
        Log.e(TAG, "answer: " + answers.get("5"));
        Log.e(TAG, "answer: " + answers.get("6"));

        mSubmitView.showProgress();
        mSessionInteractor.getAllMeasurements(answers)
                .subscribe(this::handleMeasurementsReceived, this::handleError);
    }

    @Override
    public void obtainEvent(Intent intent) {
        mEventModel = intent.getParcelableExtra(EXTRA_EVENT_MODEL);
    }

    private void handleMeasurementsReceived(Void object) {
        mSessionInteractor.submitResults()
                .subscribe(this::handleResultsSubmitted, this::handleError);
    }

    private void handleResultsSubmitted(ResponseBody responseBody) {
        mSessionInteractor.finishSession(mEventModel.getId())
                .subscribe(this::handleSessionFinished, this::handleError);
    }

    private void handleSessionFinished(FinishSessionResponseModel finishSessionResponseModel) {
        mSessionInteractor.cleanUp()
                .subscribe(this::cleanUpFinished, this::handleError);
    }

    private void cleanUpFinished(Object object) {
        mSubmitView.hideProgress();
        mSubmitView.finishSession();
    }

    private void handleError(Throwable throwable) {
        throwable.printStackTrace();
        Logger.exc(TAG, throwable.getLocalizedMessage(), throwable);
        mSubmitView.hideProgress();
        mSubmitView.showMessage(throwable.getLocalizedMessage());
    }

}
