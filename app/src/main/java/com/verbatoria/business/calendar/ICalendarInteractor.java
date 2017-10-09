package com.verbatoria.business.calendar;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;

import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * @author nikitaremnev
 */

public interface ICalendarInteractor {

    Observable<VerbatologModel> getEvents(VerbatologModel verbatolog);

    Observable<List<EventModel>> getEvents();

    Observable<List<EventModel>> getEvents(Date startDate, Date endDate);

    Observable<ResponseBody> addEvent(EventModel eventModel);

    Observable<ResponseBody> editEvent(EventModel eventModel);


}
