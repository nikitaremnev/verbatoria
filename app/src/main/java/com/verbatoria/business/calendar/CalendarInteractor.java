package com.verbatoria.business.calendar;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.TimeIntervalModel;
import com.verbatoria.business.dashboard.processor.ModelsConverter;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.request.AddEventRequestModel;
import com.verbatoria.data.network.request.EditEventRequestModel;
import com.verbatoria.data.network.request.EventRequestModel;
import com.verbatoria.data.repositories.calendar.ICalendarRepository;
import com.verbatoria.data.repositories.calendar.comparator.EventsComparator;
import com.verbatoria.data.repositories.dashboard.IDashboardRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.utils.DateUtils;
import com.verbatoria.utils.RxSchedulers;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
    public Observable<List<EventModel>> getEvents(Date startDate, Date endDate) {
        try {
            return mCalendarRepository.getEvents(getAccessToken(),
                    DateUtils.toServerDateTimeWithoutConvertingString(startDate.getTime()),
                    DateUtils.toServerDateTimeWithoutConvertingString(endDate.getTime()))
                    .map(ModelsConverter::convertEventsResponseToVerbatologEventsModelList)
                    .map( unsortedList -> {
                        Collections.sort(unsortedList, new EventsComparator());
                        return unsortedList;
                    })
                    .doOnNext(eventModels -> mCalendarRepository.saveLastDate(startDate))
                    .subscribeOn(RxSchedulers.getNewThreadScheduler())
                    .observeOn(RxSchedulers.getMainThreadScheduler());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Observable<List<TimeIntervalModel>> getAvailableTimeIntervals(Calendar calendar) {
        try {
            return mCalendarRepository.getEvents(getAccessToken(),
                    DateUtils.toServerDateTimeWithoutConvertingString(getFromTimeInMillis(calendar)),
                    DateUtils.toServerDateTimeWithoutConvertingString(getToTimeInMillis(calendar)))
                    .map( eventsResponseModel -> ModelsConverter.convertEventsResponseToTimeIntervalsList(calendar, eventsResponseModel))
                    .subscribeOn(RxSchedulers.getNewThreadScheduler())
                    .observeOn(RxSchedulers.getMainThreadScheduler());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
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

    @Override
    public Observable<ResponseBody> editEvent(EventModel eventModel) {
        try {
            return mCalendarRepository.editEvent(eventModel.getId(), getAccessToken(), getEditEventRequestModel(eventModel))
                    .subscribeOn(RxSchedulers.getNewThreadScheduler())
                    .observeOn(RxSchedulers.getMainThreadScheduler());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Observable<ResponseBody> deleteEvent(EventModel eventModel) {
        return mCalendarRepository.deleteEvent(eventModel.getId(), getAccessToken())
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Date getLastDate() {
        return mCalendarRepository.getLastDate();
    }

    private AddEventRequestModel getAddEventRequestModel(EventModel eventModel) throws ParseException {
        return new AddEventRequestModel()
                .setEvent(new EventRequestModel()
                    .setChildId(eventModel.getChild().getId())
                    .setLocationId(mDashboardRepository.getLocationId())
                    .setStartAt(DateUtils.toServerDateTimeWithoutConvertingString(eventModel.getStartAt().getTime()))
                    .setEndAt(DateUtils.toServerDateTimeWithoutConvertingString(eventModel.getEndAt().getTime())));
    }

    private EditEventRequestModel getEditEventRequestModel(EventModel eventModel) throws ParseException {
        return new EditEventRequestModel()
                .setEvent(new EventRequestModel()
                        .setChildId(eventModel.getChild().getId())
                        .setLocationId(mDashboardRepository.getLocationId())
                        .setStartAt(DateUtils.toServerDateTimeWithoutConvertingString(eventModel.getStartAt().getTime()))
                        .setEndAt(DateUtils.toServerDateTimeWithoutConvertingString(eventModel.getEndAt().getTime())));
    }

    private String getAccessToken() {
        TokenModel tokenModel = mTokenRepository.getToken();
        return tokenModel.getAccessToken();
    }

    private long getFromTimeInMillis(Calendar calendar) {
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    private Date getFromDate(Calendar calendar) {
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    private long getToTimeInMillis(Calendar calendar) {
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTimeInMillis();
    }

}
