package com.verbatoria.presentation.calendar.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.view.ICalendarView;
import com.verbatoria.utils.Logger;

import java.util.List;

/**
 * Реализация презентера для календаря
 *
 * @author nikitaremnev
 */
public class CalendarPresenter implements ICalendarPresenter {

    private static final String TAG = CalendarPresenter.class.getSimpleName();

    private IDashboardInteractor mDashboardInteractor;
    private ICalendarView mCalendarView;

    public CalendarPresenter(IDashboardInteractor dashboardInteractor) {
        mDashboardInteractor = dashboardInteractor;
    }

    @Override
    public void bindView(@NonNull ICalendarView calendarView) {
        mCalendarView = calendarView;
    }

    @Override
    public void unbindView() {
        mCalendarView = null;
    }

    @Override
    public void updateVerbatologEvents() {
        mDashboardInteractor.getVerbatologEvents()
                .subscribe(this::handleVerbatologEventsReceived, this::handleVerbatologEventsLoadingFailed);
    }

    private void handleVerbatologEventsReceived(@NonNull List<EventModel> eventModelList) {
        Logger.e(TAG, eventModelList.toString());
        mCalendarView.showVerbatologEvents(eventModelList);
    }

    private void handleVerbatologEventsLoadingFailed(Throwable throwable) {
        throwable.printStackTrace();
        Logger.exc(TAG, throwable);
    }

}
