package com.verbatoria.presentation.dashboard.presenter.calendar.add;

import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.presentation.dashboard.view.calendar.add.IAddCalendarEventView;

/**
 * Реализация презентера для экрана добавления события
 *
 * @author nikitaremnev
 */
public class AddCalendarEventPresenter implements IAddCalendarEventPresenter {

    private IDashboardInteractor mDashboardInteractor;
    private IAddCalendarEventView mAddCalendarEventView;

    public AddCalendarEventPresenter(IDashboardInteractor dashboardInteractor) {
        mDashboardInteractor = dashboardInteractor;
    }

    @Override
    public void bindView(@NonNull IAddCalendarEventView addCalendarEventView) {
        mAddCalendarEventView = addCalendarEventView;
    }

    @Override
    public void unbindView() {
        mAddCalendarEventView = null;
    }

    @Override
    public void addEvent(String childId, String locationId, String startDate, String endDate) {

    }
}
