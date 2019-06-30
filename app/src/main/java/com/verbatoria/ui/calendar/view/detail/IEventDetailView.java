package com.verbatoria.ui.calendar.view.detail;

import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.ReportModel;
import com.verbatoria.business.dashboard.models.TimeIntervalModel;
import com.verbatoria.data.network.common.ClientModel;

import java.util.List;

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

    void updateClientView(ClientModel clientModel);
    void updateChildView(ChildModel childModel);
    void updateReportView(ReportModel reportModel);
    void updateArchimedView(boolean isArchimedFieldEnabled, boolean isArchimed);
    void updateHobbyView(boolean isHobbyEnabledForAge, boolean isHobby);

    void updateEventTime(EventModel eventModel);
    void updateSendToLocationView(ReportModel reportModel, boolean isSent);
    void updateIncludeAttentionMemoryView(ReportModel reportModel, boolean isSent);

    void setUpEventCreated();
    void setUpEventEdit();

    void showConfirmSendToLocation();
    void showConfirmIncludeAttentionMemory();
    void showConfirmClearDatabase();
    void showConfirmHobby();

    void showPossibleTimeIntervals(List<TimeIntervalModel> timeIntervals);

    void closeWhenDeleted();

    void showConfirmOverrideWriting();

    void showError(String message);

    void showHintMessage(int messageStringResource);

    void showSuccessMessage(int messageStringResource);

    void showError(int errorStringResource);

    void showSchoolMode();

}