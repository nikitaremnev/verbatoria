package com.verbatoria.presentation.calendar.presenter.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.data.network.response.StartSessionResponseModel;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.view.detail.IEventDetailView;
import com.verbatoria.utils.Logger;
import com.verbatoria.utils.RxSchedulers;

/**
 * Реализация презентера для экрана события
 *
 * @author nikitaremnev
 */
public class EventDetailPresenter extends BasePresenter implements IEventDetailPresenter {

    private static final String TAG = EventDetailPresenter.class.getSimpleName();
    public static final String EXTRA_EVENT_MODEL = "com.verbatoria.presentation.dashboard.presenter.calendar.EXTRA_EVENT_MODEL";

    private ISessionInteractor mSessionInteractor;
    private IEventDetailView mCalendarEventDetailView;
    private EventModel mEventModel;
    private boolean mIsEditMode;

    public EventDetailPresenter(ISessionInteractor sessionInteractor) {
        mSessionInteractor = sessionInteractor;
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
        mSessionInteractor.startSession(mEventModel.getId())
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler())
                .subscribe(this::handleSessionStarted, this::handleSessionStartError);
        mCalendarEventDetailView.showProgress();
    }

    @Override
    public void obtainEvent(Intent intent) {
        mEventModel = intent.getParcelableExtra(EXTRA_EVENT_MODEL);
        if (mEventModel != null) {
            mIsEditMode = true;
        }
    }

    @Override
    public EventModel getEvent() {
        return mEventModel;
    }

    @Override
    public String getTime() {
        return mEventModel.getEventTime();
    }

    @Override
    public String getClient() {
        return "Клиент";
    }

    @Override
    public String getChild() {
        return "Ребенок";
    }

    @Override
    public ChildModel getChildModel() {
        return mEventModel.getChild();
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

    private void handleSessionStartError(Throwable throwable) {
        throwable.printStackTrace();
        Logger.exc(TAG, throwable);
        mCalendarEventDetailView.showError(throwable.getMessage());
        mCalendarEventDetailView.hideProgress();
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
