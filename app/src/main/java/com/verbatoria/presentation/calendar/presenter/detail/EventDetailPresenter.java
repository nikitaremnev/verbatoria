package com.verbatoria.presentation.calendar.presenter.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.remnev.verbatoriamini.R;
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

    private static final String TAG = EventDetailPresenter.class.getSimpleName();
    public static final String EXTRA_EVENT_MODEL = "com.verbatoria.presentation.dashboard.presenter.calendar.EXTRA_EVENT_MODEL";
    public static final int EVENT_LENGTH_MINUTES = 60;

    private IClientsInteractor mClientsInteractor;
    private ISessionInteractor mSessionInteractor;
    private ICalendarInteractor mCalendarInteractor;
    private IDashboardInteractor mDashboardInteractor;
    private IEventDetailView mCalendarEventDetailView;
    private EventModel mEventModel;
    private ClientModel mClientModel;
    private boolean mIsEditMode;

    public EventDetailPresenter(ISessionInteractor sessionInteractor,
                                ICalendarInteractor calendarInteractor,
                                IClientsInteractor clientsInteractor,
                                IDashboardInteractor dashboardInteractor) {
        mSessionInteractor = sessionInteractor;
        mCalendarInteractor = calendarInteractor;
        mClientsInteractor = clientsInteractor;
        mDashboardInteractor = dashboardInteractor;
    }

    @Override
    public void bindView(@NonNull IEventDetailView calendarEventDetailView) {
        mCalendarEventDetailView = calendarEventDetailView;
    }

    @Override
    public void unbindView() {
        mCalendarEventDetailView = null;
    }

    @Override
    public boolean checkStartSession() {
        if (mEventModel != null && mEventModel.getReport() != null) {
            switch (mEventModel.getReport().getStatus()) {
                case ReportModel.STATUS.READY:
                case ReportModel.STATUS.SENT:
                case ReportModel.STATUS.UPLOADED:
                    mCalendarEventDetailView.showConfirmOverrideWriting();
                    return true;
            }
        }
        return false;
    }

    @Override
    public void clearDatabase() {
        mCalendarEventDetailView.showProgress();
        addSubscription(mSessionInteractor.clearDatabases()
                .doOnUnsubscribe(() -> mCalendarEventDetailView.hideProgress())
                .subscribe(this::handleDatabaseCleared, this::handleError));
    }

    @Override
    public void startSession() {
        if (!hasError()) {
            addSubscription(mSessionInteractor.startSession(mEventModel.getId())
                    .doOnSubscribe(() -> mCalendarEventDetailView.showProgress())
                    .doOnUnsubscribe(() -> mCalendarEventDetailView.hideProgress())
                    .subscribe(this::handleSessionStarted, this::handleError));
        }
    }

    @Override
    public void checkDatabaseClear() {
        addSubscription(mSessionInteractor.isDatabasesClear()
                .doOnSubscribe(() -> mCalendarEventDetailView.showProgress())
                .doOnUnsubscribe(() -> mCalendarEventDetailView.hideProgress())
                .subscribe(this::handleIsDatabaseClear, this::handleError));
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
            addSubscription(mCalendarInteractor.editEvent(mEventModel)
                    .doOnSubscribe(() -> mCalendarEventDetailView.showProgress())
                    .doOnUnsubscribe(() -> mCalendarEventDetailView.hideProgress())
                    .subscribe(this::handleEventEditedOrCreated, this::handleError));
        }
    }

    @Override
    public void onDeleteEventClicked() {
        addSubscription(mCalendarInteractor.deleteEvent(mEventModel)
                .doOnSubscribe((action) -> mCalendarEventDetailView.showProgress())
                .doOnUnsubscribe(() -> mCalendarEventDetailView.hideProgress())
                .subscribe(this::handleEventDeleted, this::handleError));
    }

    @Override
    public void obtainEvent(Intent intent) {
        mEventModel = intent.getParcelableExtra(EXTRA_EVENT_MODEL);
        if (mEventModel != null) {
            mIsEditMode = true;
        } else {
            mEventModel = new EventModel();
        }
    }

    @Override
    public EventModel getEvent() {
        return mEventModel;
    }

    @Override
    public String getTime() {
        return mEventModel != null && mEventModel.getEndAt() != null && mEventModel.getStartAt() != null ? mEventModel.getEventTime() : "";
    }

    @Override
    public ChildModel getChildModel() {
        return mEventModel != null ? mEventModel.getChild() : null;
    }

    @Override
    public ClientModel getClientModel() {
        return mClientModel;
    }

    @Override
    public void loadClient() {
        if (mEventModel != null && mEventModel.getChild() != null) {
            addSubscription(mClientsInteractor.getClient(mEventModel.getChild().getClientId())
                    .doOnSubscribe(() -> mCalendarEventDetailView.showProgress())
                    .doOnUnsubscribe(() -> mCalendarEventDetailView.hideProgress())
                    .subscribe(this::handleClientLoaded, this::handleError));
        }
    }

    @Override
    public void setClientModel(ClientModel clientModel) {
        mClientModel = clientModel;
        mCalendarEventDetailView.updateClientView(mClientModel);
    }

    @Override
    public void setChildModel(ChildModel childModel) {
        mEventModel.setChild(childModel);
        mCalendarEventDetailView.updateChildView(childModel);
    }

    @Override
    public void setEventDate(Calendar calendar) {
        mEventModel.setStartAt(calendar.getTime());
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + EVENT_LENGTH_MINUTES);
        mEventModel.setEndAt(calendar.getTime());
        mCalendarEventDetailView.updateEventTime(mEventModel);
    }

    @Override
    public void onSendReportToLocationClicked() {
        mCalendarEventDetailView.showConfirmSendToLocation();
    }

    @Override
    public void onSendReportToLocationConfirmed() {
        addSubscription(
                mSessionInteractor.sendReportToLocation(mEventModel.getReport().getId())
                        .doOnSubscribe((action)-> mCalendarEventDetailView.showProgress())
                        .doOnUnsubscribe(() -> mCalendarEventDetailView.hideProgress())
                        .subscribe(this::handleReportSentToLocation, this::handleReportSendToLocationError)
        );
    }

    @Override
    public void onIncludeAttentionMemoryClicked() {
        mCalendarEventDetailView.showConfirmIncludeAttentionMemory();
    }

    @Override
    public void onIncludeAttentionMemoryConfirmed() {
        addSubscription(
                mSessionInteractor.includeAttentionMemory(mEventModel.getReport().getId())
                        .doOnSubscribe((action)-> mCalendarEventDetailView.showProgress())
                        .doOnUnsubscribe(() -> mCalendarEventDetailView.hideProgress())
                        .subscribe(this::handleAttentionMemoryIncluded, this::handleAttentionMemoryIncludeError)
        );
    }

    @Override
    public boolean isEditMode() {
        return mIsEditMode;
    }

    @Override
    public boolean isArchimedesAllowedForVerbatolog() {
        return mDashboardInteractor.isArchimedAllowedForVerbatolog();
    }

    @Override
    public boolean isArchimedesAllowedForChildAge() {
        if (mEventModel.getChild() != null) {
            int childAge = mEventModel.getChild().getAge();
            List<AgeGroupModel> ageGroupModels = mDashboardInteractor.getAgeGroupsFromCache();
            for (AgeGroupModel ageGroup : ageGroupModels) {
                if (childAge >= ageGroup.getMinAge() && childAge <= ageGroup.getMaxAge()) {
                    return ageGroup.isIsArchimedAllowed();
                }
            }
        }
        return false;
    }

    @Override
    public void pickTime(Calendar calendar) {
        addSubscription(
                mCalendarInteractor.getAvailableTimeIntervals(calendar)
                        .doOnSubscribe(() -> mCalendarEventDetailView.showProgress())
                        .doOnUnsubscribe(() -> mCalendarEventDetailView.hideProgress())
                        .subscribe(this::handleTimeIntervalsReceived, this::handleError)
        );
    }

    private void addEvent() {
        if (isArchimedesAllowedForVerbatolog() && isArchimedesAllowedForChildAge()) {
            mEventModel.setArchimed(true);
        } else {
            mEventModel.setArchimed(false);
        }
        mEventModel.setIsInstantReport(true);
        addSubscription(mCalendarInteractor.addEvent(mEventModel)
                .doOnSubscribe(() -> mCalendarEventDetailView.showProgress())
                .doOnUnsubscribe(() -> mCalendarEventDetailView.hideProgress())
                .subscribe(this::handleEventEditedOrCreated, this::handleError));
    }

    private void handleSessionStarted(@NonNull StartSessionResponseModel sessionResponseModel) {
        Logger.e(TAG, sessionResponseModel.toString());
        mSessionInteractor.saveSessionId(sessionResponseModel.getId());
        mCalendarEventDetailView.hideProgress();
        mCalendarEventDetailView.startConnection();
    }

    private void handleClientLoaded(@NonNull ClientModel clientModel) {
        Logger.e(TAG, clientModel.toString());
        this.mClientModel = clientModel;
        mCalendarEventDetailView.updateClientView(mClientModel);
    }

    private void handleEventEditedOrCreated(@NonNull EventModel eventModel) {
        if (!mIsEditMode) {
            mCalendarEventDetailView.showHintMessage(R.string.event_detail_event_added);
        } else {
            mCalendarEventDetailView.showHintMessage(R.string.event_detail_event_edited);
        }
        mIsEditMode = true;
        mEventModel = eventModel;
        mCalendarEventDetailView.updateReportView(eventModel.getReport());
        mCalendarEventDetailView.updateArchimedView(isArchimedesAllowedForVerbatolog(), isArchimedesAllowedForChildAge());
        mCalendarInteractor.saveLastDate(mEventModel.getStartAt());
        mCalendarEventDetailView.setUpEventCreated();
    }

    private void handleEventDeleted() {
        mCalendarEventDetailView.closeWhenDeleted();
    }

    private void handleError(Throwable throwable) {
        throwable.printStackTrace();
        Logger.exc(TAG, throwable);
        mCalendarEventDetailView.showError(throwable.getMessage());
    }

    private void handleTimeIntervalsReceived(List<TimeIntervalModel> timeIntervals) {
        mCalendarEventDetailView.showPossibleTimeIntervals(timeIntervals);
    }

    private void handleReportSentToLocation() {
        mCalendarEventDetailView.updateSendToLocationView(mEventModel.getReport(), true);
        mCalendarEventDetailView.showSuccessMessage(R.string.event_confirm_send_report_to_location_success);
    }

    private void handleReportSendToLocationError(Throwable throwable) {
        try {
            HttpException error = (HttpException) throwable;
            String errorBody = error.response().errorBody().string()
                    .replace("{\"error\":\"", "")
                    .replace("\"}", "");
            mCalendarEventDetailView.showError(errorBody);
        } catch (IOException e) {
            e.printStackTrace();
            mCalendarEventDetailView.showError(throwable.getMessage());
        }
    }

    private void handleAttentionMemoryIncluded() {
        mCalendarEventDetailView.updateIncludeAttentionMemoryView(mEventModel.getReport(), true);
        mCalendarEventDetailView.showSuccessMessage(R.string.event_confirm_attention_memory_include_success);
    }

    private void handleIsDatabaseClear(boolean isClear) {
        if (isClear) {
            startSession();
        } else {
            mCalendarEventDetailView.showConfirmClearDatabase();
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
            mCalendarEventDetailView.showError(errorBody);
        } catch (IOException e) {
            e.printStackTrace();
            mCalendarEventDetailView.showError(throwable.getMessage());
        }
    }

    private boolean hasError() {
        if (mClientModel == null || !mClientModel.isFull()) {
            mCalendarEventDetailView.showError(R.string.client_data_is_not_full);
            return true;
        }
        if (mEventModel == null || mEventModel.getChild() == null || !mEventModel.getChild().isFull()) {
            mCalendarEventDetailView.showError(R.string.child_data_is_not_full);
            return true;
        }
        if (mEventModel == null || !mEventModel.hasTime()) {
            mCalendarEventDetailView.showError(R.string.time_is_not_set);
            return true;
        }
        return false;
    }

    @Override
    public void saveState(Bundle outState) {
        //TODO: do all
    }

    @Override
    public void restoreState(Bundle savedInstanceState) {
        //TODO: do all
    }
}
