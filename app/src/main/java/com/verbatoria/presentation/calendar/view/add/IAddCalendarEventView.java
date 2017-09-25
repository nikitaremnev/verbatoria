package com.verbatoria.presentation.calendar.view.add;

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
