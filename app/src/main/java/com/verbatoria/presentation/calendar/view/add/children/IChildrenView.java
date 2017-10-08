package com.verbatoria.presentation.calendar.view.add.children;

/**
 * Интерфейс вьюхи для добавления события в календарь
 *
 * @author nikitaremnev
 */
public interface IChildrenView {

    //отображение прогресса
    void showProgress();
    void hideProgress();

    void setUpEditableMode();
    void setUpReadonlyMode();
    void setUpNewChildMode();

    String getChildName();

    void finishWithResult();

    void showError(String message);

    void showChildAdded();
    void showChildEdited();

    void startDatePicker();

    void updateBirthday();

}
