package com.verbatoria.business.dashboard.processor;

import com.verbatoria.business.dashboard.DashboardInteractorException;
import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.LocationModel;
import com.verbatoria.business.dashboard.models.ReportModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.data.network.response.ChildResponseModel;
import com.verbatoria.data.network.response.EventResponseModel;
import com.verbatoria.data.network.response.EventsResponseModel;
import com.verbatoria.data.network.response.LocationResponseModel;
import com.verbatoria.data.network.response.ReportResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;
import com.verbatoria.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для конвертации моделей ответа с сервера в модели используемые в приложении
 *
 * @author nikitaremnev
 */
public class ModelsConverter {

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

    public static LocationModel convertLocationResponseToLocationModel(LocationResponseModel locationResponseModel) {
        LocationModel locationModel = new LocationModel();
        locationModel.setName(locationResponseModel.getName());
        locationModel.setAddress(locationResponseModel.getAddress());
        locationModel.setCity(locationResponseModel.getCity().getName());
        locationModel.setCountry(locationResponseModel.getCity().getCountry().getName());
        locationModel.setPartner(locationResponseModel.getPartner().getName());
        return locationModel;
    }

    public static ChildModel convertChildResponseModelToChildModel(ChildResponseModel childResponseModel) {
        ChildModel childModel = new ChildModel();
        childModel.setId(childResponseModel.getId());
        return childModel;
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
            eventModel.setStartAt(DateUtils.parseDateTime(eventResponseModel.getStartAt()));
            eventModel.setEndAt(DateUtils.parseDateTime(eventResponseModel.getEndAt()));
        } catch (ParseException e) {
            throw new DashboardInteractorException(e.getMessage());
        }
        eventModel.setChild(convertVerbatologChildResponseToEventModel(eventResponseModel.getChild()));
        eventModel.setReport(convertReportResponseModelToReportModel(eventResponseModel.getReport()));
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

    private static ReportModel convertReportResponseModelToReportModel(ReportResponseModel reportResponseModel) throws DashboardInteractorException {
        ReportModel reportModel = new ReportModel();
        reportModel.setId(reportResponseModel.getId());
        reportModel.setChildId(reportResponseModel.getChildId());
        reportModel.setStatus(reportResponseModel.getStatus());
        try {
            if (reportResponseModel.getCreatedAt() != null) {
                reportModel.setCreatedAt(DateUtils.parseDate(reportResponseModel.getCreatedAt()));
            }
            if (reportResponseModel.getUpdatedAt() != null) {
                reportModel.setUpdatedAt(DateUtils.parseDate(reportResponseModel.getUpdatedAt()));
            }
        } catch (ParseException e) {
            throw new DashboardInteractorException(e.getMessage());
        }
        reportModel.setLocationId(reportResponseModel.getLocationId());
        reportModel.setVerbatologId(reportResponseModel.getVerbatologId());
        reportModel.setReportId(reportResponseModel.getReportId());
        return reportModel;
    }
}
