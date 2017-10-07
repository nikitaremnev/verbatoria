package com.verbatoria.presentation.dashboard.view.main;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.LocationModel;

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
    void showLocationInfo(LocationModel locationModel);

    void showVerbatologEvents(List<EventModel> verbatologEvents);
}
