package com.verbatoria.presentation.session.presenter.submit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.presentation.session.view.connection.ConnectionActivity;
import com.verbatoria.presentation.session.view.submit.ISubmitView;
import com.verbatoria.utils.FileUtils;
import com.verbatoria.utils.Logger;
import com.verbatoria.utils.PreferencesStorage;
import com.verbatoria.utils.RxSchedulers;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

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
        mSessionInteractor.getAllMeasurements(answers)
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler())
                .subscribe(this::handleMeasurementsReceived, this::handleError);
    }

    private void handleMeasurementsReceived(Void object) {
        File file = new File(FileUtils.getApplicationDirectory(), PreferencesStorage.getInstance().getLastReportName());
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/json"), file);

        mSessionInteractor.submitResults(fileBody)
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler())
                .subscribe(this::handleResultsSubmitted, this::handleError);
    }

    private void handleResultsSubmitted(ResponseBody responseBody) {
        Observable.fromCallable(() -> {
                mSessionInteractor.cleanUp();
                return null;
            }).subscribeOn(RxSchedulers.getNewThreadScheduler())
            .observeOn(RxSchedulers.getMainThreadScheduler())
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
