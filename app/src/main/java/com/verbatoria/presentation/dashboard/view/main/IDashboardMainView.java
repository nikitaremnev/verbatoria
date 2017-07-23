package com.verbatoria.presentation.dashboard.view.main;

import com.verbatoria.business.dashboard.models.EventModel;

import java.util.List;

/**
 *
 * View для экрана dashboard
 *
 * @author nikitaremnev
 */
public interface IDashboardMainView {

    //отображение прогресса
    void showProgress();
    void hideProgress();

    void showVerbatologInfo(String verbatologFullName, String verbatologPhone, String verbatologEmail);
    void showVerbatologEvents(List<EventModel> verbatologEvents);
}
