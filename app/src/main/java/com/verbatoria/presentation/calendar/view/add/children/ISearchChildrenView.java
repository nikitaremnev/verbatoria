package com.verbatoria.presentation.calendar.view.add.children;

import com.verbatoria.business.dashboard.models.ChildModel;

import java.util.List;

/**
 * Интерфейс вьюхи для добавления события в календарь
 *
 * @author nikitaremnev
 */
public interface ISearchChildrenView {

    //отображение прогресса
    void showProgress();
    void hideProgress();

    String getQuery();

    void showChildsFound(List<ChildModel> childModels);

    void showError(String message);

}
