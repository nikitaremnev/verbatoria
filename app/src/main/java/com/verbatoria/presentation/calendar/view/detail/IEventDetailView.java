package com.verbatoria.presentation.calendar.view.detail;

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
    void showError(String message);

    void showEventAdded();
    void showEventEdited();

    void updateClientView(ClientModel clientModel);
    void updateChildView(ChildModel childModel);
    void updateReportView(ReportModel reportModel);
    void updateInstantReportView(boolean showInstantReportField, boolean isInstantReport);
    void updateEventTime(EventModel eventModel);
    void updateSendToLocationView(ReportModel reportModel, boolean isSent);
    void updateIncludeAttentionMemoryView(ReportModel reportModel, boolean isSent);

    void setUpEventCreated();
    void setUpEventEdit();

    void showClientNotFullError();
    void showChildNotFullError();

    void showConfirmSendToLocation();
    void showConfirmIncludeAttentionMemory();
    void showConfirmClearDatabase();

    void showConfirmInstantReport();
    void showSentToLocationSuccess();
    void showSentToLocationError(String error);

    void showIncludeAttentionMemorySuccess();
    void showIncludeAttentionMemoryError(String error);

    void showPossibleTimeIntervals(List<TimeIntervalModel> timeIntervals);

    void closeWhenDeleted();

    void showConfirmOverrideWriting();
}
