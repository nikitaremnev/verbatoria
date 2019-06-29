package com.verbatoria.ui.schedule.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.verbatoria.business.schedule.IScheduleInteractor;
import com.verbatoria.business.schedule.datasource.IScheduleDataSource;
import com.verbatoria.business.schedule.models.ScheduleItemModel;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.ui.schedule.view.IScheduleView;

/**
 * Реализация презентера для экрана расписания
 *
 * @author nikitaremnev
 */
public class SchedulePresenter extends BasePresenter implements ISchedulePresenter {

    private static final String TAG = SchedulePresenter.class.getSimpleName();

    private IScheduleInteractor mScheduleInteractor;
    private IScheduleView mScheduleView;

    private IScheduleDataSource mScheduleDataSource;

    public SchedulePresenter(IScheduleInteractor scheduleInteractor) {
        mScheduleInteractor = scheduleInteractor;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mScheduleView.showProgress();
        mScheduleInteractor.getSchedule()
                .doOnComplete(() -> mScheduleView.hideProgress())
                .subscribe(this::handleScheduleReceived, this::handleScheduleError);
    }

    @Override
    public void bindView(@NonNull IScheduleView scheduleView) {
        mScheduleView = scheduleView;
    }

    @Override
    public void unbindView() {
        mScheduleView = null;
    }

    @Override
    public void onItemClicked(int row, int column) {
        ScheduleItemModel scheduleItemModel = (ScheduleItemModel) mScheduleDataSource.getItemData(row, column);
        scheduleItemModel.setSelected(!scheduleItemModel.isSelected());
        mScheduleView.notifyItemChanged(row, column, scheduleItemModel.isSelected());
    }

    @Override
    public void onClearScheduleClicked() {
        mScheduleView.showScheduleClearConfirmation();
    }

    @Override
    public void onNextWeekClicked() {
        mScheduleView.showProgress();
        mScheduleInteractor.getScheduleNextWeek(mScheduleDataSource)
                .doOnComplete(() -> mScheduleView.hideProgress())
                .subscribe(this::handleScheduleReceived, this::handleScheduleError);
    }

    @Override
    public void onPreviousWeekClicked() {
        mScheduleView.showProgress();
        mScheduleInteractor.getSchedulePreviousWeek(mScheduleDataSource)
                .doOnComplete(() -> mScheduleView.hideProgress())
                .subscribe(this::handleScheduleReceived, this::handleScheduleError);
    }

    @Override
    public void onSaveScheduleClicked() {
        mScheduleView.confirmSaveSchedule();
    }

    @Override
    public void clearSchedule() {
        mScheduleView.showProgress();
        mScheduleInteractor
                .deleteSchedule(mScheduleDataSource, 0)
                .doOnComplete(() -> mScheduleView.hideProgress())
                .subscribe(this::handleScheduleCleared, this::handleScheduleError);
    }

    @Override
    public void saveSchedule(int weeksForwardCount) {
        mScheduleView.showProgress();
        mScheduleInteractor.deleteSchedule(mScheduleDataSource, weeksForwardCount)
            .subscribe(this::handleScheduleDeleted, this::handleScheduleError);
    }

    @Override
    public void saveState(Bundle outState) {

    }

    @Override
    public void restoreState(Bundle savedInstanceState) {

    }

    private void handleScheduleReceived(IScheduleDataSource scheduleDataSource) {
        mScheduleDataSource = scheduleDataSource;
        mScheduleView.setUpAdapter(scheduleDataSource);
        mScheduleView.showScheduleLoaded(mScheduleDataSource.getWeekTitle());
    }

    private void handleScheduleDeleted(int weeksForwardCount) {
        mScheduleView.showProgress();
        mScheduleInteractor.saveSchedule(mScheduleDataSource, weeksForwardCount)
                .doOnComplete(() -> mScheduleView.hideProgress())
                .subscribe(this::handleScheduleSaved, this::handleScheduleError);
    }

    private void handleScheduleCleared(int weeksForwardCount) {
        mScheduleDataSource.clearSchedule();
        mScheduleView.notifyScheduleCleared();
        mScheduleView.showScheduleSaved();
    }

    private void handleScheduleSaved(IScheduleDataSource scheduleDataSource) {
        mScheduleDataSource = scheduleDataSource;
        mScheduleView.setUpAdapter(scheduleDataSource);
        mScheduleView.showScheduleSaved();
    }

    private void handleScheduleError(Throwable throwable) {
        mScheduleView.showError(throwable.getLocalizedMessage());
        throwable.printStackTrace();
    }
}
