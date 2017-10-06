package com.verbatoria.presentation.dashboard.presenter.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.verbatoria.business.calendar.ICalendarInteractor;
import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.presentation.dashboard.view.main.IDashboardMainView;
import com.verbatoria.utils.Logger;

/**
 * Реализация презентера для dashboard
 *
 * @author nikitaremnev
 */
public class DashboardMainPresenter implements IDashboardMainPresenter {

    private static final String TAG = DashboardMainPresenter.class.getSimpleName();

    private IDashboardInteractor mDashboardInteractor;
    private ICalendarInteractor mCalendarInteractor;
    private IDashboardMainView mDashboardView;
    private VerbatologModel mVerbatologModel;

    public DashboardMainPresenter(IDashboardInteractor dashboardInteractor, ICalendarInteractor calendarInteractor) {
        mDashboardInteractor = dashboardInteractor;
        mCalendarInteractor = calendarInteractor;
    }

    @Override
    public void bindView(@NonNull IDashboardMainView dashboardView) {
        mDashboardView = dashboardView;
        mVerbatologModel = new VerbatologModel();
    }

    @Override
    public void unbindView() {
        mDashboardView = null;
        mVerbatologModel = null;
    }

    @Override
    public void updateVerbatologInfo() {
        mDashboardInteractor.getVerbatologInfo(mVerbatologModel)
                .subscribe(this::handleVerbatologInfoReceived, this::handleVerbatologInfoLoadingFailed);
    }

    @Override
    public void updateVerbatologEvents() {
        Log.e(TAG, "get verbatolog events updateVerbatologEvents");
        mCalendarInteractor.getVerbatologEvents(mVerbatologModel)
                .subscribe(this::handleVerbatologEventsReceived, this::handleVerbatologEventsLoadingFailed);
    }

    private void handleVerbatologInfoReceived(@NonNull VerbatologModel verbatologModel) {
        Logger.e(TAG, verbatologModel.toString());
        mDashboardView.showVerbatologInfo(verbatologModel.getFullName(), verbatologModel.getPhone(), verbatologModel.getEmail());
    }

    private void handleVerbatologInfoLoadingFailed(Throwable throwable) {
        mDashboardInteractor.getVerbatologInfoFromCache()
                .subscribe(this::handleVerbatologInfoReceived, this::handleVerbatologEventsLoadingFailed);
    }

    private void handleVerbatologEventsReceived(@NonNull VerbatologModel verbatologModel) {
        Logger.e(TAG, verbatologModel.toString());
        mDashboardView.showVerbatologEvents(verbatologModel.getEvents());
    }

    private void handleVerbatologEventsLoadingFailed(Throwable throwable) {
        throwable.printStackTrace();
        Logger.exc(TAG, throwable);
    }
}
