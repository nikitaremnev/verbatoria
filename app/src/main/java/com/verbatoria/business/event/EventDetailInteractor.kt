package com.verbatoria.business.event

import com.remnev.verbatoria.R
import com.verbatoria.domain.schedule.TimeSlot
import com.verbatoria.domain.client.Client
import com.verbatoria.business.event.models.item.*
import com.verbatoria.domain.client.ClientManager
import com.verbatoria.domain.dashboard.calendar.CalendarManager
import com.verbatoria.domain.dashboard.calendar.Event
import com.verbatoria.domain.dashboard.info.InfoManager
import com.verbatoria.domain.report.ReportManager
import com.verbatoria.domain.schedule.ScheduleManager
import com.verbatoria.infrastructure.extensions.formatToTime
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.ui.event.EventDetailMode
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author n.remnev
 */

private const val MINIMUM_HOBBY_AGE = 18

interface EventDetailInteractor {

    fun getCreateNewModeEventDetailItems(): Single<List<EventDetailItem>>

    fun getStartModeEventDetailItems(event: Event): Single<List<EventDetailItem>>

    fun getClient(cliendId: String): Single<Client>

    fun createNewEvent(childId: String, childAge: Int, startAt: Date, endAt: Date): Completable

    fun editEvent(event: Event): Completable

    fun deleteEvent(eventId: String): Completable

    fun getAvailableTimeSlots(date: Date): Single<Pair<List<TimeSlot>, ArrayList<String>>>

    fun sendReportToLocation(reportId: String): Completable

    fun includeAttentionMemory(reportId: String): Completable

}

class EventDetailInteractorImpl(
    private val calendarManager: CalendarManager,
    private val scheduleManager: ScheduleManager,
    private val clientManager: ClientManager,
    private val reportManager: ReportManager,
    private val infoManager: InfoManager,
    private val schedulersFactory: RxSchedulersFactory
) : EventDetailInteractor {

    override fun getCreateNewModeEventDetailItems(): Single<List<EventDetailItem>> =
        Single.fromCallable {
            listOf(
                EventDetailHeaderItem(
                    mode = EventDetailMode.CREATE_NEW,
                    headerStringResource = R.string.client
                ),
                EventDetailClientItem(
                    mode =EventDetailMode.CREATE_NEW,
                    isLoading = false
                ),
                EventDetailHeaderItem(
                    mode = EventDetailMode.CREATE_NEW,
                    headerStringResource = R.string.child
                ),
                EventDetailChildItem(
                    EventDetailMode.CREATE_NEW
                ),
                EventDetailHeaderItem(
                    mode = EventDetailMode.CREATE_NEW,
                    headerStringResource = R.string.time
                ),
                EventDetailTimeItem(
                    mode = EventDetailMode.CREATE_NEW
                ),
                EventDetailSubmitItem(
                    EventDetailMode.CREATE_NEW
                )
            )
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun getStartModeEventDetailItems(event: Event): Single<List<EventDetailItem>> =
        Single.fromCallable {
            val eventDetailItems = mutableListOf(
                EventDetailHeaderItem(
                    mode = EventDetailMode.START,
                    headerStringResource = R.string.client
                ),
                EventDetailClientItem(
                    EventDetailMode.START
                ),
                EventDetailHeaderItem(
                    mode = EventDetailMode.START,
                    headerStringResource = R.string.child
                ),
                EventDetailChildItem(
                    EventDetailMode.START,
                    name = event.child.name,
                    age = event.child.age
                ),
                EventDetailHeaderItem(
                    mode = EventDetailMode.START,
                    headerStringResource = R.string.time
                ),
                EventDetailTimeItem(
                    mode = EventDetailMode.START,
                    startDate = event.startDate,
                    endDate = event.endDate
                ),
                EventDetailHeaderItem(
                    mode = EventDetailMode.START,
                    headerStringResource = R.string.report
                ),
                EventDetailReportItem(
                    mode = EventDetailMode.START,
                    reportId = event.report.reportId,
                    reportStatus = event.report.status
                )
            )

            if (!infoManager.isSchool() && event.report.isSent()) {
                eventDetailItems.add(
                    EventDetailHeaderItem(
                        mode = EventDetailMode.START,
                        headerStringResource = R.string.event_detail_location
                    )
                )
                eventDetailItems.add(
                    EventDetailSendToLocationItem(
                        mode = EventDetailMode.START
                    )
                )
            }

            if (!infoManager.isSchool() && event.report.isSentOrReady()) {
                eventDetailItems.add(
                    EventDetailHeaderItem(
                        mode = EventDetailMode.START,
                        headerStringResource = R.string.attention_memory
                    )
                )
                eventDetailItems.add(
                    EventDetailIncludeAttentionMemoryItem(
                        mode = EventDetailMode.START
                    )
                )
            }

            if (!infoManager.isSchool() && event.isArchimedesAllowed) {
                eventDetailItems.add(
                    EventDetailHeaderItem(
                        mode = EventDetailMode.START,
                        headerStringResource = R.string.arhimedes
                    )
                )
                eventDetailItems.add(
                    EventDetailArchimedesItem(
                        mode = EventDetailMode.START,
                        isArchimedesIncluded = infoManager.isArchimedesAllowedForVerbatolog()
                    )
                )
            }

            if (!infoManager.isSchool() && event.child.age  >= MINIMUM_HOBBY_AGE) {
                eventDetailItems.add(
                    EventDetailHeaderItem(
                        mode = EventDetailMode.START,
                        headerStringResource = R.string.hobby
                    )
                )
                eventDetailItems.add(
                    EventDetailHobbyItem(
                        mode = EventDetailMode.START,
                        isHobbyIncluded = event.isHobbyIncluded
                    )
                )
            }

            eventDetailItems.toList()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun getClient(cliendId: String): Single<Client> =
        Single.fromCallable {
            clientManager.getClient(cliendId)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun createNewEvent(
        childId: String,
        childAge: Int,
        startAt: Date,
        endAt: Date
    ): Completable =
        Completable.fromCallable {
            calendarManager.createNewEvent(childId, childAge, startAt, endAt)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun editEvent(
        event: Event
    ): Completable =
        Completable.fromCallable {
            calendarManager.editEvent(event.id, event.child.id ?: throw IllegalStateException("Try to edit event while event child id is null"), event.child.age, event.startDate, event.endDate, event.isHobbyIncluded)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun deleteEvent(eventId: String): Completable =
        Completable.fromCallable {
            calendarManager.deleteEvent(eventId)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun getAvailableTimeSlots(date: Date): Single<Pair<List<TimeSlot>, ArrayList<String>>> =
        Single.zip(
            Single.fromCallable {
                calendarManager.getEventsForDate(date)
            },
            Single.fromCallable {
                scheduleManager.loadScheduleForDay(date)
            },
            BiFunction { events: List<Event>, timeSlots: List<TimeSlot> ->
                //intersection function
                Pair(timeSlots, ArrayList(timeSlots.map {
                    it.startTime.formatToTime() + " - " + it.endTime.formatToTime()
                }))
            }
        )
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun sendReportToLocation(reportId: String): Completable =
        Completable.fromCallable {
            reportManager.sendReportToLocation(reportId)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun includeAttentionMemory(reportId: String): Completable =
        Completable.fromCallable {
            reportManager.includeAttentionMemory(reportId)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}