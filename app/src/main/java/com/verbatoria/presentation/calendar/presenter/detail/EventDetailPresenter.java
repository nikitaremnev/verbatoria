package com.verbatoria.presentation.calendar.presenter.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.remnev.verbatoria.R;
import com.verbatoria.business.calendar.ICalendarInteractor;
import com.verbatoria.business.clients.IClientsInteractor;
import com.verbatoria.business.dashboard.IDashboardInteractor;
import com.verbatoria.business.dashboard.models.AgeGroupModel;
import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.ReportModel;
import com.verbatoria.business.dashboard.models.TimeIntervalModel;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.data.network.response.StartSessionResponseModel;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.view.detail.IEventDetailView;
import com.verbatoria.utils.Logger;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import retrofit2.HttpException;

/**
 * Реализация презентера для экрана события
 *
 * @author nikitaremnev
 */
public class EventDetailPresenter extends BasePresenter implements IEventDetailPresenter {

    private static final String TAG = "EventDetailPresenter";
    private static final int MINIMUM_HOBBY_AGE = 18;

    public static final int EVENT_LENGTH_MINUTES = 60;
    public static final String EXTRA_EVENT_MODEL = "com.verbatoria.presentation.dashboard.presenter.calendar.EXTRA_EVENT_MODEL";

    private IClientsInteractor clientsInteractor;
    private ISessionInteractor sessionInteractor;
    private ICalendarInteractor calendarInteractor;
    private IDashboardInteractor dashboardInteractor;

    private EventModel eventModel;
    private ClientModel clientModel;

    private IEventDetailView eventDetailView;

    private boolean isEditMode;
    private boolean isSchoolAccount;

    public EventDetailPresenter(ISessionInteractor sessionInteractor,
                                ICalendarInteractor calendarInteractor,
                                IClientsInteractor clientsInteractor,
                                IDashboardInteractor dashboardInteractor,
                                boolean isSchoolAccount) {
        this.sessionInteractor = sessionInteractor;
        this.calendarInteractor = calendarInteractor;
        this.clientsInteractor = clientsInteractor;
        this.dashboardInteractor = dashboardInteractor;
        this.isSchoolAccount = isSchoolAccount;
    }

    @Override
    public void bindView(@NonNull IEventDetailView eventDetailView) {
        this.eventDetailView = eventDetailView;
    }

    @Override
    public void onViewsAreSet() {
        if (isSchoolAccount) {
            eventDetailView.showSchoolMode();
        }
    }

    @Override
    public void unbindView() {
        eventDetailView = null;
    }

    @Override
    public boolean checkStartSession() {
        if (eventModel != null && eventModel.getReport() != null) {
            switch (eventModel.getReport().getStatus()) {
                case ReportModel.STATUS.READY:
                case ReportModel.STATUS.SENT:
                case ReportModel.STATUS.UPLOADED:
                    eventDetailView.showConfirmOverrideWriting();
                    return true;
            }
        }
        return false;
    }

    @Override
    public void clearDatabase() {
        eventDetailView.showProgress();
        addSubscription(sessionInteractor.clearDatabases()
                .doOnUnsubscribe(() -> eventDetailView.hideProgress())
                .subscribe(this::handleDatabaseCleared, this::handleError));
    }

    @Override
    public void startSession() {
        if (!hasError()) {
            addSubscription(sessionInteractor.startSession(eventModel.getId())
                    .doOnSubscribe(() -> eventDetailView.showProgress())
                    .doOnUnsubscribe(() -> eventDetailView.hideProgress())
                    .subscribe(this::handleSessionStarted, this::handleError));
        }
    }

    @Override
    public void checkDatabaseClear() {
        addSubscription(sessionInteractor.hasMeasurements()
                .doOnSubscribe(() -> eventDetailView.showProgress())
                .doOnUnsubscribe(() -> eventDetailView.hideProgress())
                .subscribe(this::handleHasMeasurements, this::handleError));
    }

    @Override
    public void onCreateEventClicked() {
        if (!hasError()) {
            addEvent();
        }
    }

    @Override
    public void onEditEventClicked() {
        if (!hasError()) {
            addSubscription(calendarInteractor.editEvent(eventModel)
                    .doOnSubscribe(() -> eventDetailView.showProgress())
                    .doOnUnsubscribe(() -> eventDetailView.hideProgress())
                    .subscribe(this::handleEventEditedOrCreated, this::handleError));
        }
    }

    @Override
    public void onDeleteEventClicked() {
        addSubscription(calendarInteractor.deleteEvent(eventModel)
                .doOnSubscribe((action) -> eventDetailView.showProgress())
                .doOnUnsubscribe(() -> eventDetailView.hideProgress())
                .subscribe(this::handleEventDeleted, this::handleError));
    }

    @Override
    public void obtainEvent(Intent intent) {
        eventModel = intent.getParcelableExtra(EXTRA_EVENT_MODEL);
        if (eventModel != null) {
            isEditMode = true;
        } else {
            eventModel = new EventModel();
        }
    }

    @Override
    public EventModel getEvent() {
        return eventModel;
    }

    @Override
    public String getTime() {
        return eventModel != null && eventModel.getEndAt() != null && eventModel.getStartAt() != null ? eventModel.getEventTime() : "";
    }

    @Override
    public ChildModel getChildModel() {
        return eventModel != null ? eventModel.getChild() : null;
    }

    @Override
    public ClientModel getClientModel() {
        return clientModel;
    }

    @Override
    public void loadClient() {
        if (eventModel != null && eventModel.getChild() != null) {
            addSubscription(clientsInteractor.getClient(eventModel.getChild().getClientId())
                    .doOnSubscribe(() -> eventDetailView.showProgress())
                    .doOnUnsubscribe(() -> eventDetailView.hideProgress())
                    .subscribe(this::handleClientLoaded, this::handleError));
        }
    }

    @Override
    public void setClientModel(ClientModel clientModel) {
        this.clientModel = clientModel;
        eventDetailView.updateClientView(this.clientModel);
    }

    @Override
    public void setChildModel(ChildModel childModel) {
        eventModel.setChild(childModel);
        eventDetailView.updateChildView(childModel);
        if (isEditMode) {
            if (!eventModel.getArchimed() && isArchimedesAllowedForVerbatolog() && isArchimedesAllowedForChildAge()) {
                eventModel.setArchimed(true);
                onEditEventClicked();
            } else if (eventModel.getArchimed() && (!isArchimedesAllowedForVerbatolog() || !isArchimedesAllowedForChildAge())) {
                eventModel.setArchimed(false);
                onEditEventClicked();
            }
        }
    }

    @Override
    public void setEventDate(Calendar calendar) {
        eventModel.setStartAt(calendar.getTime());
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + EVENT_LENGTH_MINUTES);
        eventModel.setEndAt(calendar.getTime());
        eventDetailView.updateEventTime(eventModel);
    }

    @Override
    public void onSendReportToLocationClicked() {
        eventDetailView.showConfirmSendToLocation();
    }

    @Override
    public void onSendReportToLocationConfirmed() {
        addSubscription(
                sessionInteractor.sendReportToLocation(eventModel.getReport().getId())
                        .doOnSubscribe((action)-> eventDetailView.showProgress())
                        .doOnUnsubscribe(() -> eventDetailView.hideProgress())
                        .subscribe(this::handleReportSentToLocation, this::handleReportSendToLocationError)
        );
    }

    @Override
    public void onIncludeAttentionMemoryClicked() {
        eventDetailView.showConfirmIncludeAttentionMemory();
    }

    @Override
    public void onIncludeAttentionMemoryConfirmed() {
        addSubscription(
                sessionInteractor.includeAttentionMemory(eventModel.getReport().getId())
                        .doOnSubscribe((action)-> eventDetailView.showProgress())
                        .doOnUnsubscribe(() -> eventDetailView.hideProgress())
                        .subscribe(this::handleAttentionMemoryIncluded, this::handleAttentionMemoryIncludeError)
        );
    }

    //region hobby

    @Override
    public void onHobbyClicked() {
        eventDetailView.showConfirmHobby();
    }

    @Override
    public void onHobbyConfirmed() {
        eventModel.setHobby(true);
        onEditEventClicked();
    }

    //endregion

    @Override
    public boolean isEditMode() {
        return isEditMode;
    }

    @Override
    public boolean isArchimedesAllowedForVerbatolog() {
        return dashboardInteractor.isArchimedAllowedForVerbatolog();
    }

    @Override
    public boolean isArchimedesAllowedForChildAge() {
        if (eventModel.getChild() != null) {
            int childAge = eventModel.getChild().getAge();
            List<AgeGroupModel> ageGroupModels = dashboardInteractor.getAgeGroupsFromCache();
            for (AgeGroupModel ageGroup : ageGroupModels) {
                if (childAge >= ageGroup.getMinAge() && childAge <= ageGroup.getMaxAge()) {
                    return ageGroup.isIsArchimedAllowed();
                }
            }
        }
        return false;
    }

    @Override
    public boolean isHobbyAllowedForChildAge() {
        if (eventModel.getChild() != null) {
            int childAge = eventModel.getChild().getAge();
            return childAge >= MINIMUM_HOBBY_AGE;
        }
        return false;
    }

    @Override
    public void pickTime(Calendar calendar) {
        addSubscription(
                calendarInteractor.getAvailableTimeIntervals(calendar)
                        .doOnSubscribe(() -> eventDetailView.showProgress())
                        .doOnUnsubscribe(() -> eventDetailView.hideProgress())
                        .subscribe(this::handleTimeIntervalsReceived, this::handleError)
        );
    }

    private void addEvent() {
        if (isArchimedesAllowedForVerbatolog() && isArchimedesAllowedForChildAge()) {
            eventModel.setArchimed(true);
        } else {
            eventModel.setArchimed(false);
        }
        eventModel.setIsInstantReport(true);
        addSubscription(calendarInteractor.addEvent(eventModel)
                .doOnSubscribe(() -> eventDetailView.showProgress())
                .doOnUnsubscribe(() -> eventDetailView.hideProgress())
                .subscribe(this::handleEventEditedOrCreated, this::handleError));
    }

    private void handleSessionStarted(@NonNull StartSessionResponseModel sessionResponseModel) {
        Logger.e(TAG, sessionResponseModel.toString());
        sessionInteractor.saveSessionId(sessionResponseModel.getId());
        sessionInteractor.saveAge(eventModel.getChild().getAge());
        eventDetailView.hideProgress();
        eventDetailView.startConnection();
    }

    private void handleClientLoaded(@NonNull ClientModel clientModel) {
        Logger.e(TAG, clientModel.toString());
        this.clientModel = clientModel;
        eventDetailView.updateClientView(this.clientModel);
    }

    private void handleEventEditedOrCreated(@NonNull EventModel eventModel) {
        if (!isEditMode) {
            eventDetailView.showHintMessage(R.string.event_detail_event_added);
        } else {
            eventDetailView.showHintMessage(R.string.event_detail_event_edited);
        }
        isEditMode = true;
        this.eventModel = eventModel;
        eventDetailView.updateReportView(eventModel.getReport());
        if (isSchoolAccount) {
            eventDetailView.showSchoolMode();
        } else {
            eventDetailView.updateArchimedView(isArchimedesAllowedForVerbatolog(), isArchimedesAllowedForChildAge());
            eventDetailView.updateHobbyView(isHobbyAllowedForChildAge(), eventModel.getHobby());
        }
        calendarInteractor.saveLastDate(this.eventModel.getStartAt());
        eventDetailView.setUpEventCreated();
    }

    private void handleEventDeleted() {
        eventDetailView.closeWhenDeleted();
    }

    private void handleError(Throwable throwable) {
        throwable.printStackTrace();
        Logger.exc(TAG, throwable);
        eventDetailView.showError(throwable.getMessage());
    }

    private void handleTimeIntervalsReceived(List<TimeIntervalModel> timeIntervals) {
        eventDetailView.showPossibleTimeIntervals(timeIntervals);
    }

    private void handleReportSentToLocation() {
        eventDetailView.updateSendToLocationView(eventModel.getReport(), true);
        eventDetailView.showSuccessMessage(R.string.event_confirm_send_report_to_location_success);
    }

    private void handleReportSendToLocationError(Throwable throwable) {
        try {
            HttpException error = (HttpException) throwable;
            String errorBody = error.response().errorBody().string()
                    .replace("{\"error\":\"", "")
                    .replace("\"}", "");
            eventDetailView.showError(errorBody);
        } catch (IOException e) {
            e.printStackTrace();
            eventDetailView.showError(throwable.getMessage());
        }
    }

    private void handleAttentionMemoryIncluded() {
        eventDetailView.updateIncludeAttentionMemoryView(eventModel.getReport(), true);
        eventDetailView.showSuccessMessage(R.string.event_confirm_attention_memory_include_success);
    }

    private void handleHasMeasurements(boolean hasMeasurements) {
        if (hasMeasurements) {
            eventDetailView.showConfirmClearDatabase();
        } else {
            startSession();
        }
    }

    private void handleDatabaseCleared() {
        startSession();
    }

    private void handleAttentionMemoryIncludeError(Throwable throwable) {
        try {
            HttpException error = (HttpException) throwable;
            String errorBody = error.response().errorBody().string()
                    .replace("{\"error\":\"", "")
                    .replace("\"}", "");
            eventDetailView.showError(errorBody);
        } catch (IOException e) {
            e.printStackTrace();
            eventDetailView.showError(throwable.getMessage());
        }
    }

    private boolean hasError() {
        if (clientModel == null || !clientModel.isFull()) {
            eventDetailView.showError(R.string.client_data_is_not_full);
            return true;
        }
        if (eventModel == null || eventModel.getChild() == null || !eventModel.getChild().isFull()) {
            eventDetailView.showError(R.string.child_data_is_not_full);
            return true;
        }
        if (eventModel == null || !eventModel.hasTime()) {
            eventDetailView.showError(R.string.time_is_not_set);
            return true;
        }
        return false;
    }

    @Override
    public void saveState(Bundle outState) {
        //empty
    }

    @Override
    public void restoreState(Bundle savedInstanceState) {
        //empty
    }
}
