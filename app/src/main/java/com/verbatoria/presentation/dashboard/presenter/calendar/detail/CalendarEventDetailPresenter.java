package com.verbatoria.presentation.dashboard.presenter.calendar.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.presentation.dashboard.view.calendar.detail.ICalendarEventDetailView;
import com.verbatoria.utils.RxSchedulers;

/**
 * Реализация презентера для экрана события
 *
 * @author nikitaremnev
 */
public class CalendarEventDetailPresenter implements ICalendarEventDetailPresenter {

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
//                .subscribe(this::handleVerbatologEventsReceived, this::handleVerbatologEventsLoadingFailed);

        mSessionInteractor.startSession();
    }

    @Override
    public void obtainEvent(Intent intent) {
        mEventModel = intent.getParcelableExtra(EXTRA_EVENT_MODEL);
    }

}
