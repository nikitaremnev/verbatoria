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

    void onInstantReportConfirmed();
    void onInstantReportDeclined();

    void onInstantReportStateChanged(boolean isInstantReport);

    boolean isEditMode();
    boolean isArchimedesAllowedForVerbatolog();
    boolean isArchimedesAllowedForChildAge();

    void pickTime(Calendar calendar);

}
