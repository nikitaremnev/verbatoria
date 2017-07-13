package com.verbatoria.presentation.dashboard.presenter.calendar;

import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.presentation.dashboard.presenter.settings.ISettingsPresenter;
import com.verbatoria.presentation.dashboard.view.calendar.ICalendarView;
import com.verbatoria.presentation.dashboard.view.settings.ISettingsView;

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


}
