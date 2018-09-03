package com.verbatoria.business.dashboard.processor;

import com.verbatoria.business.dashboard.DashboardInteractorException;
import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.LocationModel;
import com.verbatoria.business.dashboard.models.ReportModel;
import com.verbatoria.business.dashboard.models.TimeIntervalModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.data.network.response.ChildResponseModel;
import com.verbatoria.data.network.response.EventResponseModel;
import com.verbatoria.data.network.response.EventsResponseModel;
import com.verbatoria.data.network.response.LocationResponseModel;
import com.verbatoria.data.network.response.ReportResponseModel;
import com.verbatoria.data.network.response.ScheduleItemResponseModel;
import com.verbatoria.data.network.response.ScheduleResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;
import com.verbatoria.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.verbatoria.utils.LocaleHelper.LOCALE_RU;

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
        locationModel.setId(locationResponseModel.getId());
        locationModel.setName(locationResponseModel.getName());
        locationModel.setAddress(locationResponseModel.getAddress());
        locationModel.setCity(locationResponseModel.getCity().getName());
        locationModel.setCountry(locationResponseModel.getCity().getCountry().getName());
        locationModel.setPartner(locationResponseModel.getPartner().getName());
        locationModel.setAvailableLocales(locationResponseModel.getAvailableLocales());
        locationModel.setLocale(locationResponseModel.getLocale());
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

    private static final int HOUR_START = 9;
    private static final int HOUR_FINISH = 21;

    public static List<TimeIntervalModel> convertEventsResponseToTimeIntervalsList(Calendar calendar,
                                                                                   EventsResponseModel eventsResponseModel,
                                                                                   ScheduleResponseModel scheduleResponseModel) {
        List<TimeIntervalModel> timeIntervalModels = new ArrayList<>();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, HOUR_START);
        calendar.set(Calendar.SECOND, 0);
        Calendar calendarHelper = Calendar.getInstance(new Locale(LOCALE_RU));
        for (int i = HOUR_START + 1; i < HOUR_FINISH; i ++) {
            TimeIntervalModel timeIntervalModel = new TimeIntervalModel();
            timeIntervalModel.setStartAt(calendar.getTime());
            calendar.set(Calendar.HOUR_OF_DAY, i);
            timeIntervalModel.setEndAt(calendar.getTime());
            calendarHelper.setTime(timeIntervalModel.getStartAt());
            int timeIntervalStartHour = calendarHelper.get(Calendar.HOUR_OF_DAY);
            for (int j = 0; j < scheduleResponseModel.getScheduleItems().size(); j ++) {
                EventModel eventModel = convertScheduleItemEventResponseToEventModel(scheduleResponseModel.getScheduleItems().get(j));
                calendarHelper.setTime(eventModel.getStartAt());
                if (calendarHelper.get(Calendar.HOUR_OF_DAY) == timeIntervalStartHour) {
                    timeIntervalModels.add(timeIntervalModel);
                    break;
                }
            }
        }

        calendarHelper = Calendar.getInstance(new Locale(LOCALE_RU));
        for (int i = 0; i < eventsResponseModel.getEvents().size(); i ++) {
            EventModel eventModel = convertVerbatologEventResponseToEventModel(eventsResponseModel.getEvents().get(i));
            calendarHelper.setTime(eventModel.getStartAt());
            int startCalendarHour = calendarHelper.get(Calendar.HOUR_OF_DAY);
            for (int j = 0; j < timeIntervalModels.size(); j ++) {
                TimeIntervalModel timeIntervalModel = timeIntervalModels.get(j);
                calendarHelper.setTime(timeIntervalModel.getStartAt());
                int currentStartHour = calendarHelper.get(Calendar.HOUR_OF_DAY);
                if (startCalendarHour == currentStartHour && !eventModel.getReport().isCanceled()) {
                    timeIntervalModels.remove(j);
                    break;
                }
            }
        }

        return timeIntervalModels;
    }

    public static EventModel convertVerbatologEventResponseToEventModel(EventResponseModel eventResponseModel) {
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
        eventModel.setIsInstantReport(eventResponseModel.getInstantReport());
        return eventModel;
    }

    private static EventModel convertScheduleItemEventResponseToEventModel(ScheduleItemResponseModel scheduleItemResponseModel) {
        EventModel eventModel = new EventModel();
        eventModel.setId(scheduleItemResponseModel.getId());
        try {
            eventModel.setStartAt(DateUtils.parseDateTime(scheduleItemResponseModel.getFromTime()));
            eventModel.setEndAt(DateUtils.parseDateTime(scheduleItemResponseModel.getToTime()));
        } catch (ParseException e) {
            throw new DashboardInteractorException(e.getMessage());
        }
        return eventModel;
    }

    private static ChildModel convertVerbatologChildResponseToEventModel(ChildResponseModel childResponseModel) throws DashboardInteractorException {
        ChildModel childModel = new ChildModel();
        childModel.setId(childResponseModel.getId());
        childModel.setClientId(childResponseModel.getClientId());
        childModel.setName(childResponseModel.getName());
        childModel.setGender(childResponseModel.getGender());
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
