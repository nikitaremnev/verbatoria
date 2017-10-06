package com.verbatoria.presentation.calendar.presenter.add.children;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.presentation.calendar.view.add.children.IChildrenView;

import java.util.Date;

/**
 * Презентер для добавления события в календарь
 *
 * @author nikitaremnev
 */
public interface IChildrenPresenter {

    void bindView(@NonNull IChildrenView childrenView);
    void unbindView();

    void obtainChild(Intent intent);
    ChildModel getChildModel();

    boolean isEditMode();

    void createChild();
    void editChild();

    String getChildName();
    String getChildBirthday();

    void setChildBirthday(Date birthday);
}
