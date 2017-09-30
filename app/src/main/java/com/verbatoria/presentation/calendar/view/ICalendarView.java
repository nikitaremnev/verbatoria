package com.verbatoria.presentation.calendar.view;

import com.verbatoria.business.dashboard.models.EventModel;

import java.util.List;

/**
 * Интерфейс вьюхи для отображения календаря
 *
 * @author nikitaremnev
 */
public interface ICalendarView {

    void showVerbatologEvents(List<EventModel> verbatologEvents);

}