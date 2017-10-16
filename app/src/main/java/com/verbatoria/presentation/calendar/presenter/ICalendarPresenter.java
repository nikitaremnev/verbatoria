package com.verbatoria.presentation.calendar.presenter;

import android.support.annotation.NonNull;

import com.verbatoria.presentation.calendar.view.ICalendarView;

import java.util.Calendar;

/**
 * Презентер для календаря
 *
 * @author nikitaremnev
 */
public interface ICalendarPresenter {

    void bindView(@NonNull ICalendarView calendarView);
    void unbindView();

    void updateVerbatologEvents(Calendar calendar);

    void restoreLastDate();
}
