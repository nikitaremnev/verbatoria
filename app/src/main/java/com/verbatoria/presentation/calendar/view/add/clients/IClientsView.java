package com.verbatoria.presentation.calendar.view.add.clients;

/**
 * Интерфейс вьюхи для добавления события в календарь
 *
 * @author nikitaremnev
 */
public interface IClientsView {

    //отображение прогресса
    void showProgress();
    void hideProgress();

    void setUpEditableMode();
    void setUpReadonlyMode();
    void setUpNewClientMode();

    void finishWithResult();

    void showError(String message);

}
