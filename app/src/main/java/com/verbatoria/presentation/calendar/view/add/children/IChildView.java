package com.verbatoria.presentation.calendar.view.add.children;

import com.verbatoria.business.dashboard.models.ChildModel;

import java.util.List;

/**
 * Интерфейс вьюхи для добавления события в календарь
 *
 * @author nikitaremnev
 */
public interface IChildView {

    //отображение прогресса
    void showProgress();

    void hideProgress();

    void setUpEditableMode();

    void setUpReadonlyMode();

    void setUpNewChildMode();

    String getChildName();

    void finishWithResult(ChildModel childModel);

    void startAgePicker();

    void updateAge();

    void updateGender();

    void showPossibleChildren(List<ChildModel> children);

    void showSuccessWithCloseAfter(int messageStringResource);

    void showError(String message);

    void showError(int errorStringResource);

}
