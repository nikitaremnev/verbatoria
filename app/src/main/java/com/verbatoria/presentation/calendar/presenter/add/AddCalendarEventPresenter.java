package com.verbatoria.presentation.calendar.presenter.add;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.view.add.IAddCalendarEventView;

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
