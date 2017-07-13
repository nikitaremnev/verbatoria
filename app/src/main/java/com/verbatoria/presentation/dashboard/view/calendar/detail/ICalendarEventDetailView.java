package com.verbatoria.presentation.dashboard.view.calendar.detail;

/**
 * Интерфейс вьюхи для отображения деталей события календаря
 *
 * @author nikitaremnev
 */
public interface ICalendarEventDetailView {

    //отображение прогресса
    void showProgress();
    void hideProgress();

    void startSession();
    void showError(String message);

}
