package com.verbatoria.business.dashboard.processor;

import com.verbatoria.business.dashboard.DashboardInteractorException;
import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.data.network.response.VerbatologChildResponseModel;
import com.verbatoria.data.network.response.VerbatologEventResponseModel;
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
        return verbatologModel;
    }

    public static VerbatologModel convertEventsResponseToVerbatologModel(VerbatologModel verbatologModel,
                                                                   List<VerbatologEventResponseModel> verbatologEventResponseModelList) {
        List<EventModel> verbatologEventsList = new ArrayList<>();
        for (int i = 0; i < verbatologEventResponseModelList.size(); i ++) {
            verbatologEventsList.add(convertVerbatologEventResponseToEventModel(verbatologEventResponseModelList.get(i)));
        }
        verbatologModel.setEvents(verbatologEventsList);
        return verbatologModel;
    }

    public static List<EventModel> convertEventsResponseToVerbatologEventsModelList(List<VerbatologEventResponseModel> verbatologEventResponseModelList) {
        List<EventModel> verbatologEventsList = new ArrayList<>();
        for (int i = 0; i < verbatologEventResponseModelList.size(); i ++) {
            verbatologEventsList.add(convertVerbatologEventResponseToEventModel(verbatologEventResponseModelList.get(i)));
        }
        return verbatologEventsList;
    }

    private static EventModel convertVerbatologEventResponseToEventModel(VerbatologEventResponseModel verbatologEventResponseModel) {
        EventModel eventModel = new EventModel();
        eventModel.setId(verbatologEventResponseModel.getId());
        try {
            eventModel.setStartAt(DateUtils.parseDate(verbatologEventResponseModel.getStartAt()));
            eventModel.setEndAt(DateUtils.parseDate(verbatologEventResponseModel.getEndAt()));
        } catch (ParseException e) {
            throw new DashboardInteractorException(e.getMessage());
        }
        eventModel.setChild(convertVerbatologChildResponseToEventModel(verbatologEventResponseModel.getChild()));
        return eventModel;
    }

    private static ChildModel convertVerbatologChildResponseToEventModel(VerbatologChildResponseModel verbatologChildResponseModel) throws DashboardInteractorException {
        ChildModel childModel = new ChildModel();
        childModel.setId(verbatologChildResponseModel.getId());
        childModel.setName(verbatologChildResponseModel.getName());
        try {
            if (verbatologChildResponseModel.getBirthday() != null) {
                childModel.setBirthday(DateUtils.parseDate(verbatologChildResponseModel.getBirthday()));
            }
        } catch (ParseException e) {
            throw new DashboardInteractorException(e.getMessage());
        }
        return childModel;
    }
}
