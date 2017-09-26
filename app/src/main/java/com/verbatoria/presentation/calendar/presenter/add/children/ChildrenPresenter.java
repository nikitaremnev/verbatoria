package com.verbatoria.presentation.calendar.presenter.add.children;

import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.presentation.calendar.view.add.children.IChildrenView;

/**
 * Реализация презентера для экрана добавления события
 *
 * @author nikitaremnev
 */
public class ChildrenPresenter implements IChildrenPresenter {

    private IDashboardInteractor mDashboardInteractor;
    private IChildrenView mChildrenView;

    public ChildrenPresenter(IDashboardInteractor dashboardInteractor) {
        mDashboardInteractor = dashboardInteractor;
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
