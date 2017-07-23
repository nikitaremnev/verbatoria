package com.verbatoria.presentation.session.presenter.submit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.data.network.request.MeasurementRequestModel;
import com.verbatoria.presentation.session.view.connection.ConnectionActivity;
import com.verbatoria.presentation.session.view.submit.ISubmitView;
import com.verbatoria.utils.Logger;
import com.verbatoria.utils.RxSchedulers;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.ResponseBody;

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
    public void obtainEvent(Intent intent) {
        mEventModel = intent.getParcelableExtra(ConnectionActivity.EXTRA_EVENT_MODEL);
    }

    @Override
    public void sendResults(Map<String, String> answers) {
        mSubmitView.showProgress();
        mSessionInteractor.getAllMeasurements(answers)
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler())
                .subscribe(this::handleMeasurementsReceived, this::handleError);
    }

    private void handleMeasurementsReceived(List<MeasurementRequestModel> measurementList) {
        mSubmitView.hideProgress();
        mSubmitView.finishSession();
//        mSessionInteractor.submitResults(measurementList)
//                .subscribeOn(RxSchedulers.getNewThreadScheduler())
//                .observeOn(RxSchedulers.getMainThreadScheduler())
//                .subscribe(this::handleSessionFinished, this::handleError);
    }

    private void handleSessionFinished(ResponseBody responseBody) {
        mSubmitView.hideProgress();
        mSubmitView.finishSession();
        Logger.e(TAG, responseBody.toString());
    }

    private void handleError(Throwable throwable) {
        throwable.printStackTrace();
        Logger.exc(TAG, throwable.getLocalizedMessage(), throwable);
        mSubmitView.hideProgress();
        mSubmitView.showMessage(throwable.getLocalizedMessage());
//        mSubmitView.finishSession();
    }

}
