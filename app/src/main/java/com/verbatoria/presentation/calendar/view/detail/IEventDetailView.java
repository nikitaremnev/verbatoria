package com.verbatoria.presentation.calendar.view.detail;

import com.verbatoria.business.dashboard.models.ChildModel;
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
    void showError(String message);

    void updateClientView(ClientModel clientModel);
    void updateChildView(ChildModel childModel);

    void disableButton();
    void enableButton();

}
