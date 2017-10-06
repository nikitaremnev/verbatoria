package com.verbatoria.business.dashboard.processor;

import com.verbatoria.business.dashboard.DashboardInteractorException;
import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.data.network.response.ChildResponseModel;
import com.verbatoria.data.network.response.EventResponseModel;
import com.verbatoria.data.network.response.EventsResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;
import com.verbatoria.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для конвертации моделей Вербатолога
 *
 * @author nikitaremnev
 */
public class VerbatologProcessor {

    public static VerbatologModel convertInfoResponseToVerbatologModel(VerbatologModel verbatologModel,
                                                                 VerbatologInfoResponseModel verbatologInfoResponseModel) {
        verbatologModel.setFirstName(verbatologInfoResponseModel.getFirstName());
        verbatologModel.setLastName(verbatologInfoResponseModel.getLastName());
        verbatologModel.setMiddleName(verbatologInfoResponseModel.getMiddleName());
        verbatologModel.setEmail(verbatologInfoResponseModel.getEmail());
        verbatologModel.setPhone(verbatologInfoResponseModel.getPhone());
        verbatologModel.setLocationId(verbatologInfoResponseModel.getLocationId());
        return verbatologModel;
    }

    public static VerbatologModel convertEventsResponseToVerbatologModel(VerbatologModel verbatologModel,
                                                                   EventsResponseModel eventsResponseModel) {
        List<EventModel> verbatologEventsList = new ArrayList<>();
        for (int i = 0; i < eventsResponseModel.getEvents().size(); i ++) {
            verbatologEventsList.add(convertVerbatologEventResponseToEventModel(eventsResponseModel.getEvents().get(i)));
        }
        verbatologModel.setEvents(verbatologEventsList);
        return verbatologModel;
    }

    public static List<EventModel> convertEventsResponseToVerbatologEventsModelList(EventsResponseModel eventsResponseModel) {
        List<EventModel> verbatologEventsList = new ArrayList<>();
        for (int i = 0; i < eventsResponseModel.getEvents().size(); i ++) {
            verbatologEventsList.add(convertVerbatologEventResponseToEventModel(eventsResponseModel.getEvents().get(i)));
        }
        return verbatologEventsList;
    }

    private static EventModel convertVerbatologEventResponseToEventModel(EventResponseModel eventResponseModel) {
        EventModel eventModel = new EventModel();
        eventModel.setId(eventResponseModel.getId());
        try {
            eventModel.setStartAt(DateUtils.parseDate(eventResponseModel.getStartAt()));
            eventModel.setEndAt(DateUtils.parseDate(eventResponseModel.getEndAt()));
        } catch (ParseException e) {
            throw new DashboardInteractorException(e.getMessage());
        }
        eventModel.setChild(convertVerbatologChildResponseToEventModel(eventResponseModel.getChild()));
        return eventModel;
    }

    private static ChildModel convertVerbatologChildResponseToEventModel(ChildResponseModel childResponseModel) throws DashboardInteractorException {
        ChildModel childModel = new ChildModel();
        childModel.setId(childResponseModel.getId());
        childModel.setClientId(childResponseModel.getClientId());
        childModel.setName(childResponseModel.getName());
        try {
            if (childResponseModel.getBirthday() != null) {
                childModel.setBirthday(DateUtils.parseDate(childResponseModel.getBirthday()));
            }
        } catch (ParseException e) {
            throw new DashboardInteractorException(e.getMessage());
        }
        return childModel;
    }
}
