package com.verbatoria.ui.calendar.view.add.clients;

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
    void showClientAdded();
    void showClientEdited();
    void showEmailIncorrectError();
    void showNameOrPhoneEmptyError();

    String getClientName();
    String getClientEmail();
    String getClientPhone();

}