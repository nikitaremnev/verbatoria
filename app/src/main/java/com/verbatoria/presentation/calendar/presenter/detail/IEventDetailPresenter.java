package com.verbatoria.presentation.calendar.presenter.detail;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.presentation.calendar.view.detail.IEventDetailView;

import java.util.Calendar;

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
    void editEvent();
    void obtainEvent(Intent intent);

    EventModel getEvent();

    String getTime();

    ChildModel getChildModel();
    ClientModel getClientModel();

    void loadClient();

    void setClientModel(ClientModel clientModel);
    void setChildModel(ChildModel childModel);
    void setEventDate(Calendar calendar);

    boolean isEditMode();

}
