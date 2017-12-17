package com.verbatoria.presentation.schedule.view;

import com.verbatoria.business.schedule.datasource.IScheduleDataSource;

/**
 *
 * View для экрана расписания
 *
 * @author nikitaremnev
 */
public interface IScheduleView {

    void showProgress();

    void hideProgress();

    void setUpAdapter(IScheduleDataSource scheduleDataSource);

    void notifyItemChanged(int row, int column, boolean state);

    void notifyScheduleCleared();

    void showScheduleClearConfirmation();

    void showError(String error);

    void showScheduleSaved();

    void showScheduleLoaded(String period);

    void confirmSaveSchedule();

}
