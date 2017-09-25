package com.verbatoria.presentation.calendar.presenter.add;

import android.support.annotation.NonNull;

import com.verbatoria.presentation.calendar.view.add.IAddCalendarEventView;

/**
 * Презентер для добавления события в календарь
 *
 * @author nikitaremnev
 */
public interface IAddCalendarEventPresenter {

    void bindView(@NonNull IAddCalendarEventView addCalendarEventView);
    void unbindView();

    void addEvent(String childId, String locationId,
                  String startDate, String endDate);

}
