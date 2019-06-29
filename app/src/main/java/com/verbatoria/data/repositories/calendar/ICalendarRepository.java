package com.verbatoria.data.repositories.calendar;

import com.verbatoria.data.network.request.AddEventRequestModel;
import com.verbatoria.data.network.request.EditEventRequestModel;
import com.verbatoria.data.network.response.EventResponseModel;
import com.verbatoria.data.network.response.EventsResponseModel;

import java.util.Date;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * @author nikitaremnev
 */
public interface ICalendarRepository {

    Observable<EventsResponseModel> getEvents(String accessToken, String fromTime, String toTime);

    Observable<EventResponseModel> addEvent(String accessToken, AddEventRequestModel addEventRequestModel);

    Observable<EventResponseModel> editEvent(String eventId, String accessToken, EditEventRequestModel editEventRequestModel);

    Completable deleteEvent(String eventId, String accessToken);

    String getLocationId();

    void saveLastDate(Date fromDate);

    Date getLastDate();

}
