package com.verbatoria.business.event

import com.remnev.verbatoria.R
import com.verbatoria.domain.schedule.TimeSlot
import com.verbatoria.domain.client.Client
import com.verbatoria.business.event.models.item.*
import com.verbatoria.domain.client.ClientManager
import com.verbatoria.domain.dashboard.calendar.CalendarManager
import com.verbatoria.domain.dashboard.calendar.Event
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

    fun getViewModeEventDetailItems(event: Event): Single<List<EventDetailItem>>

    fun getClient(cliendId: String): Single<Client>

    fun createNewEvent(childId: String, childAge: Int, startAt: Date, endAt: Date): Completable

    fun getAvailableTimeSlots(date: Date): Single<Pair<List<TimeSlot>, ArrayList<String>>>

}

class EventDetailInteractorImpl(
    private val calendarManager: CalendarManager,
    private val scheduleManager: ScheduleManager,
    private val clientManager: ClientManager,
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

    override fun getViewModeEventDetailItems(event: Event): Single<List<EventDetailItem>> =
        Single.fromCallable {
            val eventDetailItems = mutableListOf(
                EventDetailHeaderItem(
                    mode = EventDetailMode.CREATE_NEW,
                    headerStringResource = R.string.client
                ),
                EventDetailClientItem(
                    EventDetailMode.CREATE_NEW
                ),
                EventDetailHeaderItem(
                    mode = EventDetailMode.CREATE_NEW,
                    headerStringResource = R.string.child
                ),
                EventDetailChildItem(
                    EventDetailMode.CREATE_NEW,
                    name = event.child.name,
                    age = event.child.age
                ),
                EventDetailHeaderItem(
                    mode = EventDetailMode.CREATE_NEW,
                    headerStringResource = R.string.time
                ),
                EventDetailTimeItem(
                    mode = EventDetailMode.CREATE_NEW,
                    startDate = event.startDate,
                    endDate = event.endDate
                )
            )
            if (event.isArchimedesAllowed) {
                EventDetailHeaderItem(
                    mode = EventDetailMode.CREATE_NEW,
                    headerStringResource = R.string.arhimedes
                )
                EventDetailArchimedesItem(
                    mode = EventDetailMode.CREATE_NEW
                )
            }

            if (event.isArchimedesAllowed) {
                eventDetailItems.add(
                    EventDetailHeaderItem(
                        mode = EventDetailMode.CREATE_NEW,
                        headerStringResource = R.string.arhimedes
                    )
                )
                eventDetailItems.add(
                    EventDetailArchimedesItem(
                        mode = EventDetailMode.CREATE_NEW
                    )
                )
            }

            if (event.isHobbyIncluded) {
                eventDetailItems.add(
                    EventDetailHeaderItem(
                        mode = EventDetailMode.CREATE_NEW,
                        headerStringResource = R.string.hobby
                    )
                )
                eventDetailItems.add(
                    EventDetailArchimedesItem(
                        mode = EventDetailMode.CREATE_NEW
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


}