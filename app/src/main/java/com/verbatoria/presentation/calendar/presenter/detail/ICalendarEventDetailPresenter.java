package com.verbatoria.presentation.calendar.presenter.detail;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.presentation.calendar.view.detail.ICalendarEventDetailView;

/**
 * Презентер для события календаря
 *
 * @author nikitaremnev
 */
public interface ICalendarEventDetailPresenter {

    void bindView(@NonNull ICalendarEventDetailView calendarEventDetailView);
    void unbindView();

    void startSession();
    void obtainEvent(Intent intent);


}
