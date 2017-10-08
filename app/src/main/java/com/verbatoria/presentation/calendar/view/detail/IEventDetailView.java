package com.verbatoria.presentation.calendar.view.detail;

import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.data.network.common.ClientModel;

/**
 * Интерфейс вьюхи для отображения деталей события календаря
 *
 * @author nikitaremnev
 */
public interface IEventDetailView {

    //отображение прогресса
    void showProgress();
    void hideProgress();

    void startConnection();
    void startChild();
    void startClient();
    void startDatePicker();
    void startTimePicker();
    void showError(String message);

    void updateClientView(ClientModel clientModel);
    void updateChildView(ChildModel childModel);
    void updateEventTime(EventModel eventModel);

    void setUpEventCreated();
    void setUpEventEdit();

    void disableButton();
    void enableButton();

    void finishWithResult();

}
