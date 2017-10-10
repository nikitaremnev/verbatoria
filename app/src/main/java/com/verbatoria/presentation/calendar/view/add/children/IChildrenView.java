package com.verbatoria.presentation.calendar.view.add.children;

import com.verbatoria.business.dashboard.models.ChildModel;

import java.util.List;

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

    void showPossibleChildren(List<ChildModel> children);

}
