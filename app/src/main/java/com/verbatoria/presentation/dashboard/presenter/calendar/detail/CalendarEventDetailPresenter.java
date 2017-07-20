package com.verbatoria.presentation.dashboard.presenter.calendar.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.data.network.response.StartSessionResponseModel;
import com.verbatoria.presentation.dashboard.presenter.calendar.CalendarPresenter;
import com.verbatoria.presentation.dashboard.view.calendar.detail.ICalendarEventDetailView;
import com.verbatoria.utils.Logger;
import com.verbatoria.utils.RxSchedulers;

/**
 * Реализация презентера для экрана события
 *
 * @author nikitaremnev
 */
public class CalendarEventDetailPresenter implements ICalendarEventDetailPresenter {

    private static final String TAG = CalendarEventDetailPresenter.class.getSimpleName();
    public static final String EXTRA_EVENT_MODEL = "com.verbatoria.presentation.dashboard.presenter.calendar.EXTRA_EVENT_MODEL";

    private ISessionInteractor mSessionInteractor;
    private ICalendarEventDetailView mCalendarEventDetailView;
    private EventModel mEventModel;

    public CalendarEventDetailPresenter(ISessionInteractor sessionInteractor) {
        mSessionInteractor = sessionInteractor;
    }

    @Override
    public void bindView(@NonNull ICalendarEventDetailView calendarEventDetailView) {
        mCalendarEventDetailView = calendarEventDetailView;
    }

    @Override
    public void unbindView() {
        mCalendarEventDetailView = null;
    }

    @Override
    public void startSession() {
        mSessionInteractor.startSession(mEventModel.getId())
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler())
                .subscribe(this::handleSessionStarted, this::handleSessionStartError);
        mCalendarEventDetailView.showProgress();
    }

    @Override
    public void obtainEvent(Intent intent) {
        mEventModel = intent.getParcelableExtra(EXTRA_EVENT_MODEL);
    }

    private void handleSessionStarted(@NonNull StartSessionResponseModel sessionResponseModel) {
        Logger.e(TAG, sessionResponseModel.toString());
        mCalendarEventDetailView.startSession();
        mCalendarEventDetailView.hideProgress();
    }

    private void handleSessionStartError(Throwable throwable) {
        throwable.printStackTrace();
        Logger.exc(TAG, throwable);
        mCalendarEventDetailView.showError(throwable.getMessage());
        mCalendarEventDetailView.hideProgress();
    }

}
