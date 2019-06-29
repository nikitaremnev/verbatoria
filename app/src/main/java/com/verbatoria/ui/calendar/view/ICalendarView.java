package com.verbatoria.ui.calendar.view;

import com.verbatoria.business.dashboard.models.EventModel;

import java.util.Date;
import java.util.List;

/**
 * Интерфейс вьюхи для отображения календаря
 *
 * @author nikitaremnev
 */
public interface ICalendarView {

    void showVerbatologEvents(List<EventModel> verbatologEvents);

    void updateTime(Date date);
}
