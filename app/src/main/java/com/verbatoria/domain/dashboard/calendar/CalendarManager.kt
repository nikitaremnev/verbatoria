package com.verbatoria.domain.dashboard.calendar

import com.verbatoria.domain.child.ChildManager
import com.verbatoria.domain.dashboard.info.InfoManager
import com.verbatoria.domain.report.Report
import com.verbatoria.domain.report.ReportStatus
import com.verbatoria.infrastructure.extensions.*
import com.verbatoria.infrastructure.retrofit.endpoints.dashboard.CalendarEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.event.EventEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.event.model.params.CreateNewOrEditEventParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.event.model.params.EventParamsDto
import java.util.*

/**
 * @author n.remnev
 */

interface CalendarManager {

    fun getLastSelectedDate(): Date

    fun saveLastSelectedDate(selectedDate: Date)

    fun getEventsForDate(date: Date): List<Event>

    fun createNewEvent(
        childId: String,
        childAge: Int,
        startAt: Date,
        endAt: Date
    )

}

class CalendarManagerImpl(
    private val infoManager: InfoManager,
    private val childManager: ChildManager,
    private val calendarEndpoint: CalendarEndpoint,
    private val eventEndpoint: EventEndpoint,
    private val calendarRepository: CalendarRepository
) : CalendarManager {

    override fun getLastSelectedDate(): Date =
        calendarRepository.getLastSelectedDate()

    override fun saveLastSelectedDate(selectedDate: Date) {
        calendarRepository.putLastSelectedDate(selectedDate)
    }

    override fun getEventsForDate(date: Date): List<Event> {
        val eventsListDto = calendarEndpoint.getEvents(
            fromTime = date.toStartDay().formatToServerTime(),
            toTime = date.toEndDay().formatToServerTime()
        )
        return eventsListDto.data.map { eventDto ->
            Event(
                id = eventDto.id,
                clientId = eventDto.child.clientId,
                startDate = eventDto.startAt.parseServerFormat(),
                endDate = eventDto.endAt.parseServerFormat(),
                isInstantReport = eventDto.isInstantReport,
                isArchimedesAllowed = eventDto.isArchimedesAllowed,
                isArchimedesIncluded = eventDto.isArchimedesIncluded,
                isHobbyIncluded = eventDto.isHobbyIncluded,
                child = childManager.parseChildDto(eventDto.child),
                report = Report(
                    id = eventDto.report.id,
                    childId = eventDto.report.id,
                    locationId = eventDto.report.locationId,
                    reportId = eventDto.report.reportId,
                    status = ReportStatus.valueOfWithDefault(eventDto.report.status),
                    createdAt = eventDto.report.createdAt.parseServerFormat(),
                    updatedAt = eventDto.report.updatedAt.parseServerFormat()
                )
            )
        }
    }

    override fun createNewEvent(childId: String, childAge: Int, startAt: Date, endAt: Date) {
        eventEndpoint.createNewEvent(
            CreateNewOrEditEventParamsDto(
                event = EventParamsDto(
                    childId = childId,
                    locationId = infoManager.getLocationId(),
                    startAt = startAt.formatToServerTime(),
                    endAt = endAt.formatToServerTime(),
                    isInstantReport = true,
                    isArchimedesIncluded = infoManager.isArchimedesAllowed() && infoManager.isAgeAvailableForArchimedes(
                        childAge
                    ),
                    isHobbyIncluded = false
                )
            )
        )
    }

}