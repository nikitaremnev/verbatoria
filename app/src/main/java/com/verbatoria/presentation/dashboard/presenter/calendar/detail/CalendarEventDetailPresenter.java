package com.verbatoria.presentation.dashboard.presenter.calendar.detail;

import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.presentation.dashboard.view.calendar.detail.ICalendarEventDetailView;

/**
 * Реализация презентера для экрана события
 *
 * @author nikitaremnev
 */
public class CalendarEventDetailPresenter implements ICalendarEventDetailPresenter {

    private IDashboardInteractor mDashboardInteractor;
    private ICalendarEventDetailView mCalendarEventDetailView;

    public CalendarEventDetailPresenter(IDashboardInteractor dashboardInteractor) {
        mDashboardInteractor = dashboardInteractor;
    }


    @Override
    public void bindView(@NonNull ICalendarEventDetailView calendarEventDetailView) {
        mCalendarEventDetailView = calendarEventDetailView;
    }

    @Override
    public void unbindView() {
        mCalendarEventDetailView = null;
    }
}
