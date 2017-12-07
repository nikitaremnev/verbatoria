package com.verbatoria.presentation.schedule.presenter;

import android.support.annotation.NonNull;

import com.verbatoria.presentation.schedule.view.IScheduleView;

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

}
