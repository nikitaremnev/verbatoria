package com.verbatoria.presentation.dashboard.presenter.add;

/**
 * Презентер для добавления события в календарь
 *
 * @author nikitaremnev
 */
public interface IAddCalendarEventPresenter {

    void addEvent(String childId, String locationId,
                  String startDate, String endDate);

}
