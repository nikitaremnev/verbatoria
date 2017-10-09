package com.verbatoria.presentation.calendar.presenter;

import android.support.annotation.NonNull;

import com.verbatoria.business.calendar.ICalendarInteractor;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.presentation.calendar.view.ICalendarView;
import com.verbatoria.utils.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Реализация презентера для календаря
 *
 * @author nikitaremnev
 */
public class CalendarPresenter implements ICalendarPresenter {

    private static final String TAG = CalendarPresenter.class.getSimpleName();

    private ICalendarInteractor mCalendarInteractor;
    private ICalendarView mCalendarView;

    public CalendarPresenter(ICalendarInteractor calendarInteractor) {
        mCalendarInteractor = calendarInteractor;
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
    public void updateVerbatologEvents(Calendar calendar) {
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        Date toDate = calendar.getTime();
        mCalendarInteractor.getEvents(fromDate, toDate)
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
