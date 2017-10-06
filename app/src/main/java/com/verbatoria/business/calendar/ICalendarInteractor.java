package com.verbatoria.business.calendar;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * @author nikitaremnev
 */

public interface ICalendarInteractor {

    Observable<VerbatologModel> getVerbatologEvents(VerbatologModel verbatolog);

    Observable<List<EventModel>> getVerbatologEvents();

    Observable<ResponseBody> addEvent(EventModel eventModel);

}
