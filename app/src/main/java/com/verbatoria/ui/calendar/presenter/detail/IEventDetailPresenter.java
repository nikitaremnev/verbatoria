package com.verbatoria.ui.calendar.presenter.detail;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.ui.calendar.view.detail.IEventDetailView;

import java.util.Calendar;

/**
 * Презентер для события календаря
 *
 * @author nikitaremnev
 */
public interface IEventDetailPresenter {

    void bindView(@NonNull IEventDetailView calendarEventDetailView);
    void onViewsAreSet();
    void unbindView();

    boolean checkStartSession();
    void clearDatabase();
    void startSession();
    void checkDatabaseClear();

    void onCreateEventClicked();
    void onEditEventClicked();
    void onDeleteEventClicked();
    void obtainEvent(Intent intent);

    EventModel getEvent();

    String getTime();

    ChildModel getChildModel();
    ClientModel getClientModel();

    void loadClient();

    void setClientModel(ClientModel clientModel);
    void setChildModel(ChildModel childModel);
    void setEventDate(Calendar calendar);

    void onSendReportToLocationClicked();
    void onSendReportToLocationConfirmed();

    void onIncludeAttentionMemoryClicked();
    void onIncludeAttentionMemoryConfirmed();

    void onHobbyClicked();
    void onHobbyConfirmed();

    boolean isEditMode();
    boolean isArchimedesAllowedForVerbatolog();
    boolean isArchimedesAllowedForChildAge();

    boolean isHobbyAllowedForChildAge();

    void pickTime(Calendar calendar);

}