package com.verbatoria.business.calendar;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.business.dashboard.processor.VerbatologProcessor;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.request.AddEventRequestModel;
import com.verbatoria.data.repositories.calendar.ICalendarRepository;
import com.verbatoria.data.repositories.dashboard.IDashboardRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.utils.DateUtils;
import com.verbatoria.utils.RxSchedulers;

import java.text.ParseException;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * @author nikitaremnev
 */

public class CalendarInteractor implements ICalendarInteractor {

    private ICalendarRepository mCalendarRepository;
    private IDashboardRepository mDashboardRepository;
    private ITokenRepository mTokenRepository;

    public CalendarInteractor(ICalendarRepository calendarRepository,
                              IDashboardRepository dashboardRepository,
                              ITokenRepository tokenRepository) {
        mCalendarRepository = calendarRepository;
        mDashboardRepository = dashboardRepository;
        mTokenRepository = tokenRepository;
    }

    @Override
    public Observable<VerbatologModel> getVerbatologEvents(VerbatologModel verbatolog) {
        return mCalendarRepository.getEvents(getAccessToken())
                .map(item -> VerbatologProcessor.convertEventsResponseToVerbatologModel(verbatolog, item))
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Observable<List<EventModel>> getVerbatologEvents() {
        return mCalendarRepository.getEvents(getAccessToken())
                .map(VerbatologProcessor::convertEventsResponseToVerbatologEventsModelList)
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Observable<ResponseBody> addEvent(EventModel eventModel) {
        try {
            return mCalendarRepository.addEvent(getAccessToken(), getAddEventRequestModel(eventModel))
                    .subscribeOn(RxSchedulers.getNewThreadScheduler())
                    .observeOn(RxSchedulers.getMainThreadScheduler());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private AddEventRequestModel getAddEventRequestModel(EventModel eventModel) throws ParseException {
        return new AddEventRequestModel()
                .setChildId(eventModel.getChild().getId())
                .setLocationId(mDashboardRepository.getLocationId())
                .setStartAt(DateUtils.toServerDateTimeString(eventModel.getStartAt().getTime()))
                .setEndAt(DateUtils.toServerDateTimeString(eventModel.getEndAt().getTime()));

    }

    private String getAccessToken() {
        TokenModel tokenModel = mTokenRepository.getToken();
        return tokenModel.getAccessToken();
    }

}
