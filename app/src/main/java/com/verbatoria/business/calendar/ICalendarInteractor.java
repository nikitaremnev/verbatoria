package com.verbatoria.business.calendar;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.TimeIntervalModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * @author nikitaremnev
 */

public interface ICalendarInteractor {

    Observable<List<EventModel>> getEvents(Date startDate, Date endDate);

//    Observable<List<TimeIntervalModel>> getAvailableTimeIntervals(Calendar day);

    Observable<EventModel> addEvent(EventModel eventModel);

    Observable<EventModel> editEvent(EventModel eventModel);

    Completable deleteEvent(EventModel eventModel);

    Date getLastDate();

    void saveLastDate(Date date);
}
