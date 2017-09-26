package com.verbatoria.presentation.calendar.presenter.add.children;

import android.support.annotation.NonNull;
import com.verbatoria.presentation.calendar.view.add.children.IChildrenView;

/**
 * Презентер для добавления события в календарь
 *
 * @author nikitaremnev
 */
public interface IChildrenPresenter {

    void bindView(@NonNull IChildrenView childrenView);
    void unbindView();

}
