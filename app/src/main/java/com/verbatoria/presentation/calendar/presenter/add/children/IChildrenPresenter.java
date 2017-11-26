package com.verbatoria.presentation.calendar.presenter.add.children;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.presentation.calendar.view.add.children.IChildrenView;
import com.verbatoria.presentation.calendar.view.add.children.search.ISearchChildrenView;

import java.util.Date;

/**
 * Презентер для добавления события в календарь
 *
 * @author nikitaremnev
 */
public interface IChildrenPresenter {

    void bindView(@NonNull ISearchChildrenView searchChildrenView);
    void bindView(@NonNull IChildrenView childrenView);
    void unbindView();

    void obtainChild(Intent intent);
    ChildModel getChildModel();

    boolean isEditMode();

    void createChild();
    void editChild();
    void searchChildren();

    void searchClientChildren();

    String getChildName();
    String getChildAge();

    void setChildBirthday(Date birthday);
}
