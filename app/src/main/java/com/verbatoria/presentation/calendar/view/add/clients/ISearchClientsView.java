package com.verbatoria.presentation.calendar.view.add.clients;

/**
 * Интерфейс вьюхи для добавления события в календарь
 *
 * @author nikitaremnev
 */
public interface ISearchClientsView {

    //отображение прогресса
    void showProgress();
    void hideProgress();

    void showError(String message);

}
