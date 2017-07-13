package com.verbatoria.presentation.dashboard.presenter.calendar;

import android.support.annotation.NonNull;

import com.verbatoria.presentation.dashboard.view.calendar.ICalendarView;

/**
 * Презентер для календаря
 *
 * @author nikitaremnev
 */
public interface ICalendarPresenter {

    void bindView(@NonNull ICalendarView calendarView);
    void unbindView();

}
