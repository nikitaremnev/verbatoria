package com.verbatoria.ui.schedule.presenter;

import android.support.annotation.NonNull;

import com.verbatoria.ui.schedule.view.IScheduleView;

/**
 * Презентер для экрана расписания
 *
 * @author nikitaremnev
 */
public interface ISchedulePresenter {

    void bindView(@NonNull IScheduleView scheduleView);

    void unbindView();

    void onItemClicked(int row, int column);

    void onClearScheduleClicked();

    void onNextWeekClicked();

    void onPreviousWeekClicked();

    void onSaveScheduleClicked();

    void clearSchedule();

    void saveSchedule(int weeksForwardCount);

}
