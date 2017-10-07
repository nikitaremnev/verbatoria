package com.verbatoria.data.repositories.calendar;


import com.verbatoria.data.network.request.AddEventRequestModel;
import com.verbatoria.data.network.request.EditEventRequestModel;
import com.verbatoria.data.network.request.GetEventsRequestModel;
import com.verbatoria.data.network.response.EventsResponseModel;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * @author nikitaremnev
 */
public interface ICalendarRepository {

    Observable<EventsResponseModel> getEvents(String accessToken, GetEventsRequestModel getEventsRequestModel);

    Observable<EventsResponseModel> getEvents(String accessToken);

    Observable<ResponseBody> addEvent(String accessToken, AddEventRequestModel addEventRequestModel);

    Observable<ResponseBody> editEvent(String eventId, String accessToken, EditEventRequestModel editEventRequestModel);

}
