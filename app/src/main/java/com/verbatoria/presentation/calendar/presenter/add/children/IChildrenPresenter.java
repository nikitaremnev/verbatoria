package com.verbatoria.presentation.calendar.presenter.add.children;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.presentation.calendar.view.add.children.IChildrenView;

/**
 * Презентер для добавления события в календарь
 *
 * @author nikitaremnev
 */
public interface IChildrenPresenter {

    void bindView(@NonNull IChildrenView childrenView);
    void unbindView();

    void obtainChild(Intent intent);
    ChildModel getEvent();

}
