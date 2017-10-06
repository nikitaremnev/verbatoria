package com.verbatoria.data.repositories.calendar;

import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.request.AddEventRequestModel;
import com.verbatoria.data.network.response.EventsResponseModel;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * @author nikitaremnev
 */
public class CalendarRepository implements ICalendarRepository {

    @Override
    public Observable<EventsResponseModel> getEvents(String accessToken) {
        return APIFactory.getAPIService().getEventsRequest(accessToken);
    }

    @Override
    public Observable<ResponseBody> addEvent(String accessToken, AddEventRequestModel addEventRequestModel) {
        return APIFactory.getAPIService().addEventRequest(accessToken, addEventRequestModel);
    }
}
