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

    void showAgeError();

    void showNameError();

    void showChildAdded();

    void showChildEdited();

    void startAgePicker();

    void updateAge();

    void updateGender();

    void showPossibleChildren(List<ChildModel> children);

}
