package com.verbatoria.presentation.calendar.view.detail;

/**
 * Интерфейс вьюхи для отображения деталей события календаря
 *
 * @author nikitaremnev
 */
public interface ICalendarEventDetailView {

    //отображение прогресса
    void showProgress();
    void hideProgress();

    void startConnection();
    void showError(String message);

}
