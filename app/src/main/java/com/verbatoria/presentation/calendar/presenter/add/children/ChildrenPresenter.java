package com.verbatoria.presentation.calendar.presenter.add.children;

import android.support.annotation.NonNull;

import com.verbatoria.business.children.IChildrenInteractor;
import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.presentation.calendar.view.add.children.IChildrenView;

/**
 * Реализация презентера для экрана данных о детях
 *
 * @author nikitaremnev
 */
public class ChildrenPresenter implements IChildrenPresenter {

    private IChildrenInteractor mChildrenInteractor;
    private IChildrenView mChildrenView;

    public ChildrenPresenter(IChildrenInteractor childrenInteractor) {
        mChildrenInteractor = childrenInteractor;
    }

    @Override
    public void bindView(@NonNull IChildrenView childrenView) {
        mChildrenView = childrenView;
    }

    @Override
    public void unbindView() {
        mChildrenView = null;
    }

}
