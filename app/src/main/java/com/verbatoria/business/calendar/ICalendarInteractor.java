package com.verbatoria.business.calendar;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.TimeIntervalModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * @author nikitaremnev
 */

public interface ICalendarInteractor {

    Observable<List<EventModel>> getEvents(Date startDate, Date endDate);

    Observable<List<TimeIntervalModel>> getAvailableTimeIntervals(Calendar day);

    Observable<ResponseBody> addEvent(EventModel eventModel);

    Observable<ResponseBody> editEvent(EventModel eventModel);

    Observable<ResponseBody> deleteEvent(EventModel eventModel);

    Date getLastDate();

}
