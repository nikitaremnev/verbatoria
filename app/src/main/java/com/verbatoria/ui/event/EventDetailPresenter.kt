package com.verbatoria.ui.event

import android.app.DatePickerDialog
import android.widget.DatePicker
import com.remnev.verbatoria.R
import com.verbatoria.domain.child.Child
import com.verbatoria.business.event.EventDetailInteractor
import com.verbatoria.business.event.models.item.*
import com.verbatoria.domain.client.Client
import com.verbatoria.domain.dashboard.calendar.Event
import com.verbatoria.domain.schedule.TimeSlot
import com.verbatoria.ui.base.BasePresenter
import com.verbatoria.ui.event.item.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

/**
 * @author n.remnev
 */

class EventDetailPresenter(
    eventDetailMode: EventDetailMode,
    private var event: Event?,
    private val eventDetailInteractor: EventDetailInteractor
) : BasePresenter<EventDetailView>(),
    EventDetailView.Callback,
    EventDetailArchimedesItemViewHolder.Callback,
    EventDetailChildItemViewHolder.Callback,
    EventDetailClientItemViewHolder.Callback,
    EventDetailTimeItemViewHolder.Callback,
    EventDetailHobbyItemViewHolder.Callback,
    EventDetailIncludeMemoryAttentionItemViewHolder.Callback,
    EventDetailReportItemViewHolder.Callback,
    EventDetailSendToLocationItemViewHolder.Callback,
    EventDetailSubmitItemViewHolder.Callback,
    DatePickerDialog.OnDateSetListener {

    private val logger: Logger = LoggerFactory.getLogger("EventDetailPresenter")

    private var currentMode: EventDetailMode = eventDetailMode

    private var eventDetailItemsList: List<EventDetailItem> = emptyList()

    private var timeSlots: List<TimeSlot> = emptyList()

    private var client: Client? = null
    private var child: Child? = null
    private var selectedTimeSlot: TimeSlot? = null

    init {

        event?.let { event ->
            getClient(event.clientId)
            child = event.child
            selectedTimeSlot = TimeSlot(
                startTime = event.startDate,
                endTime = event.endDate
            )
        }
    }

    override fun onAttachView(view: EventDetailView) {
        super.onAttachView(view)

        if (currentMode.isCreateNew()) {
            getCreateNewEventItems()
            view.setTitle(R.string.event_detail_new)
        } else {
            getViewModeEventItems(event ?: throw IllegalStateException("Event is null and event mode is not create new"))
        }
    }

    //region EventDetailView.Callback

    override fun onIntervalSelected(position: Int) {
        selectedTimeSlot = timeSlots.getOrNull(position)
        (eventDetailItemsList
            .firstOrNull { item -> item is EventDetailTimeItem }
                as? EventDetailTimeItem)
            ?.let { eventDetailDateItem ->
                eventDetailDateItem.startDate = selectedTimeSlot?.startTime
                eventDetailDateItem.endDate = selectedTimeSlot?.endTime
                view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailDateItem))
            }
        checkIsAllFieldsFilled()
    }

    override fun onClientReturned(client: Client?) {
        this.client = client
        (eventDetailItemsList
            .firstOrNull { item -> item is EventDetailClientItem }
                as? EventDetailClientItem)
            ?.let { eventDetailClientItem ->
                eventDetailClientItem.name = client?.name
                eventDetailClientItem.phone = client?.phone
                view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailClientItem))
            }
        checkIsAllFieldsFilled()
    }

    override fun onChildReturned(child: Child?) {
        this.child = child
        (eventDetailItemsList
            .firstOrNull { item -> item is EventDetailChildItem }
                as? EventDetailChildItem)
            ?.let { eventDetailChildItem ->
                eventDetailChildItem.name = child?.name
                eventDetailChildItem.age = child?.age
                view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailChildItem))
            }
        checkIsAllFieldsFilled()
    }

    override fun onNavigationClicked() {
        view?.close()
    }

    //endregion

    //region EventDetailArchimedesItemViewHolder.Callback


    //endregion

    //region EventDetailChildItemViewHolder.Callback

    override fun onChildClicked() {
        client?.let { client ->
            if (client.hasId()) {
                view?.openChild(currentMode, child, client.id!!)
            } else {
                view?.showFillClientFirstError()
            }
        } ?:  view?.showFillClientFirstError()
    }

    //endregion


    //region EventDetailClientItemViewHolder.Callback

    override fun onClientClicked() {
        view?.openClient(currentMode, client)
    }

    //endregion

    //region EventDetailTimeItemViewHolder.Callback

    override fun onDateClicked() {
        view?.showDatePickerDialog()
    }

    //endregion

    //region EventDetailHobbyItemViewHolder.Callback


    //endregion

    //region EventDetailIncludeMemoryAttentionItemViewHolder.Callback


    //endregion

    //region EventDetailReportItemViewHolder.Callback


    //endregion

    //region EventDetailSendToLocationItemViewHolder.Callback


    //endregion

    //region EventDetailSubmitItemViewHolder.Callback

    override fun onSubmitButtonClicked() {
        when (currentMode) {
            EventDetailMode.CREATE_NEW -> {
                createNewEvent()
            }
            EventDetailMode.START -> {

            }
            EventDetailMode.EDIT -> {

            }
        }
    }

    //endregion

    //region DatePickerDialog.OnDateSetListener

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        eventDetailInteractor.getAvailableTimeSlots(
            Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }.time
        )
            .subscribe({ (timeSlots, availableIntervals) ->
                this.timeSlots = timeSlots
                this.view?.showIntervalSelectionDialog(availableIntervals)
            }, { error ->
                logger.error("get create new event items models", error)
            })
            .let(::addDisposable)
    }

    //endregion

    private fun getCreateNewEventItems() {
        eventDetailInteractor.getCreateNewModeEventDetailItems()
            .subscribe({ eventDetailItems ->
                eventDetailItemsList = eventDetailItems
                view?.setEventDetailItems(eventDetailItemsList)
            }, { error ->
                logger.error("get create new event items models", error)
            })
            .let(::addDisposable)
    }

    private fun getViewModeEventItems(event: Event) {
        eventDetailInteractor.getViewModeEventDetailItems(event)
            .subscribe({ eventDetailItems ->
                eventDetailItemsList = eventDetailItems
                view?.setEventDetailItems(eventDetailItemsList)
            }, { error ->
                logger.error("get view mode event items models", error)
            })
            .let(::addDisposable)
    }

    private fun getClient(clientId: String) {
        eventDetailInteractor.getClient(clientId)
            .subscribe({ client ->
                this.client = client
                if (eventDetailItemsList.isNotEmpty()) {
                    (eventDetailItemsList
                        .firstOrNull { item -> item is EventDetailClientItem }
                            as? EventDetailClientItem)
                        ?.let { eventDetailClientItem ->
                            eventDetailClientItem.name = client?.name
                            eventDetailClientItem.phone = client?.phone
                            eventDetailClientItem.isLoading = false
                            view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailClientItem))
                        }
                }
            }, { error ->
                logger.error("get create new event items models", error)
            })
            .let(::addDisposable)
    }

    private fun createNewEvent() {
        eventDetailInteractor.createNewEvent(
            childId = child?.id
                ?: throw IllegalStateException("Try to create new event while child id is null"),
            childAge = child?.age
                ?: throw IllegalStateException("Try to create new event while child age is null"),
            startAt = selectedTimeSlot?.startTime
                ?: throw IllegalStateException("Try to create new event while start time is null"),
            endAt = selectedTimeSlot?.endTime
                ?: throw IllegalStateException("Try to create new event while start time is null")
        ).subscribe({
            view?.close()
        }, { error ->
            logger.error("create new event error occurred", error)
        })
            .let(::addDisposable)
    }

    private fun checkIsAllFieldsFilled() {
        if (client != null && child != null) {
            if (eventDetailItemsList.isNotEmpty()) {
                (eventDetailItemsList
                    .firstOrNull { item -> item is EventDetailSubmitItem }
                        as? EventDetailSubmitItem)
                    ?.let { eventDetailSubmitItem ->
                        eventDetailSubmitItem.isAllFieldsFilled = true
                        view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailSubmitItem))
                    }
            }
        } else {
            if (eventDetailItemsList.isNotEmpty()) {
                (eventDetailItemsList
                    .firstOrNull { item -> item is EventDetailSubmitItem }
                        as? EventDetailSubmitItem)
                    ?.let { eventDetailSubmitItem ->
                        eventDetailSubmitItem.isAllFieldsFilled = false
                        view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailSubmitItem))
                    }
            }
        }
    }

}