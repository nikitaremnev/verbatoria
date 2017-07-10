package com.verbatoria.presentation.dashboard.presenter.add;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.presentation.dashboard.view.calendar.add.IAddCalendarEventView;
import com.verbatoria.presentation.dashboard.view.main.IDashboardMainView;

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
    public void addEvent(String childId, String locationId, String startDate, String endDate) {

    }
}
