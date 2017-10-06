package com.verbatoria.presentation.calendar.presenter.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.verbatoria.business.calendar.ICalendarInteractor;
import com.verbatoria.business.clients.IClientsInteractor;
import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.data.network.response.StartSessionResponseModel;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.view.detail.IEventDetailView;
import com.verbatoria.utils.Logger;
import com.verbatoria.utils.RxSchedulers;

import java.util.Calendar;

import okhttp3.ResponseBody;

/**
 * Реализация презентера для экрана события
 *
 * @author nikitaremnev
 */
public class EventDetailPresenter extends BasePresenter implements IEventDetailPresenter {

    private static final String TAG = EventDetailPresenter.class.getSimpleName();
    public static final String EXTRA_EVENT_MODEL = "com.verbatoria.presentation.dashboard.presenter.calendar.EXTRA_EVENT_MODEL";

    private IClientsInteractor mClientsInteractor;
    private ISessionInteractor mSessionInteractor;
    private ICalendarInteractor mCalendarInteractor;
    private IEventDetailView mCalendarEventDetailView;
    private EventModel mEventModel;
    private ClientModel mClientModel;
    private boolean mIsEditMode;

    public EventDetailPresenter(ISessionInteractor sessionInteractor,
                                ICalendarInteractor calendarInteractor,
                                IClientsInteractor clientsInteractor) {
        mSessionInteractor = sessionInteractor;
        mCalendarInteractor = calendarInteractor;
        mClientsInteractor = clientsInteractor;
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
    public void startSession() {
        addSubscription(mSessionInteractor.startSession(mEventModel.getId())
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler())
                .doOnSubscribe(() -> mCalendarEventDetailView.showProgress())
                .doOnUnsubscribe(() -> mCalendarEventDetailView.hideProgress())
                .subscribe(this::handleSessionStarted, this::handleError));
    }

    @Override
    public void createEvent() {
        addSubscription(mCalendarInteractor.addEvent(mEventModel)
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler())
                .doOnSubscribe(() -> mCalendarEventDetailView.showProgress())
                .doOnUnsubscribe(() -> mCalendarEventDetailView.hideProgress())
                .subscribe(this::handleEventLoaded, this::handleError));
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
    public String getClient() {
        return mClientModel != null ? mClientModel.getName() : "";
    }

    @Override
    public String getChild() {
        return mEventModel != null ? mEventModel.getChild().getName() : "";
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
                    .subscribeOn(RxSchedulers.getNewThreadScheduler())
                    .observeOn(RxSchedulers.getMainThreadScheduler())
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
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 30);
        mEventModel.setEndAt(calendar.getTime());
        mCalendarEventDetailView.updateEventTime(mEventModel);
    }

    @Override
    public boolean isEditMode() {
        return mIsEditMode;
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


    private void handleEventLoaded(@NonNull ResponseBody responseBody) {
        Logger.e(TAG, responseBody.toString());
        mCalendarEventDetailView.updateClientView(mClientModel);
    }

    private void handleError(Throwable throwable) {
        throwable.printStackTrace();
        Logger.exc(TAG, throwable);
        mCalendarEventDetailView.showError(throwable.getMessage());
    }

    private void handleClientLoadingError(Throwable throwable) {
        throwable.printStackTrace();
        Logger.exc(TAG, throwable);
        mCalendarEventDetailView.showError(throwable.getMessage());
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
