package com.verbatoria.presentation.calendar.presenter.detail;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.presentation.calendar.view.detail.IEventDetailView;

/**
 * Презентер для события календаря
 *
 * @author nikitaremnev
 */
public interface IEventDetailPresenter {

    void bindView(@NonNull IEventDetailView calendarEventDetailView);
    void unbindView();

    void startSession();
    void createEvent();
    void obtainEvent(Intent intent);

    EventModel getEvent();

    String getTime();
    String getClient();
    String getChild();

    ChildModel getChildModel();

    boolean isEditMode();

}
