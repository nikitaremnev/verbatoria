package com.verbatoria.data.repositories.calendar;

import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.request.AddEventRequestModel;
import com.verbatoria.data.network.request.EditEventRequestModel;
import com.verbatoria.data.network.response.EventResponseModel;
import com.verbatoria.data.network.response.EventsResponseModel;
import com.verbatoria.utils.DateUtils;
import com.verbatoria.utils.PreferencesStorage;

import java.util.Date;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * @author nikitaremnev
 */
public class CalendarRepository implements ICalendarRepository {

    @Override
    public Observable<EventsResponseModel> getEvents(String accessToken, String fromTime, String toTime) {
        return APIFactory.getAPIService().getEventsRequest(accessToken, fromTime, toTime);
    }

    @Override
    public Observable<EventResponseModel> addEvent(String accessToken, AddEventRequestModel addEventRequestModel) {
        return APIFactory.getAPIService().addEventRequest(accessToken, addEventRequestModel);
    }

    @Override
    public Observable<EventResponseModel> editEvent(String eventId, String accessToken, EditEventRequestModel editEventRequestModel) {
        return APIFactory.getAPIService().editEventRequest(eventId, accessToken, editEventRequestModel);
    }

    @Override
    public Observable<ResponseBody> deleteEvent(String eventId, String accessToken) {
        return APIFactory.getAPIService().deleteEventRequest(eventId, accessToken);
    }

    @Override
    public String getLocationId() {
        return PreferencesStorage.getInstance().getLocationId();
    }

    @Override
    public void saveLastDate(Date fromDate) {
        PreferencesStorage.getInstance().setLastDate(DateUtils.toUIDateString(fromDate));
    }

    @Override
    public Date getLastDate() {
        try {
            return DateUtils.parseUIDateString(PreferencesStorage.getInstance().getLastDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }


}
