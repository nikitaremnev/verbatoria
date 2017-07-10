package com.verbatoria.presentation.dashboard.view.calendar.add;

/**
 * Интерфейс вьюхи для добавления события в календарь
 *
 * @author nikitaremnev
 */
public interface IAddCalendarEventView {

    //отображение прогресса
    void showProgress();
    void hideProgress();

    void addEvent();

}
