package com.verbatoria.ui.event

import android.app.DatePickerDialog
import android.widget.DatePicker
import com.remnev.verbatoria.R
import com.verbatoria.domain.child.Child
import com.verbatoria.business.event.EventDetailInteractor
import com.verbatoria.business.event.models.item.*
import com.verbatoria.domain.client.Client
import com.verbatoria.domain.dashboard.calendar.Event
import com.verbatoria.domain.report.ReportStatus
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
            view.setTitle(R.string.event_detail_create_new_mode_title)
        } else {
            getViewModeEventItems(event ?: throw IllegalStateException("Event is null and event mode is not create new"))
            view.setTitle(R.string.event_detail_start_mode_title)
        }
    }

    //region EventDetailView.Callback

    override fun onSendToLocationConfirmed() {
        findEventDetailItemInList<EventDetailSendToLocationItem>()
            ?.let { eventDetailSendToLocationItem ->
                eventDetailSendToLocationItem.isLoading = true
                view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailSendToLocationItem))
                sendReportToLocation()
            }
    }

    override fun onIncludeHobbyConfirmed() {
        findEventDetailItemInList<EventDetailHobbyItem>()
            ?.let { eventDetailHobbyItem ->
                eventDetailHobbyItem.isLoading = true
                view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailHobbyItem))
                editEventForHobby()
            }
    }

    override fun onIntervalSelected(position: Int) {
        selectedTimeSlot = timeSlots.getOrNull(position)
        findEventDetailItemInList<EventDetailTimeItem>()
            ?.let { eventDetailDateItem ->
                eventDetailDateItem.startDate = selectedTimeSlot?.startTime
                eventDetailDateItem.endDate = selectedTimeSlot?.endTime
                view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailDateItem))
            }
        checkIsAllFieldsFilled()
    }

    override fun onClientReturned(client: Client?) {
        this.client = client
        findEventDetailItemInList<EventDetailClientItem>()
            ?.let { eventDetailClientItem ->
                eventDetailClientItem.name = client?.name
                eventDetailClientItem.phone = client?.phone
                view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailClientItem))
            }
        checkIsAllFieldsFilled()
    }

    override fun onChildReturned(child: Child?) {
        this.child = child
        findEventDetailItemInList<EventDetailChildItem>()
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
        if (client != null || event != null) {
            client?.let { client ->
                if (client.hasId() ) {
                    view?.openChild(currentMode, child, client.id!!)
                } else {
                    view?.showFillClientFirstError()
                }
            } ?: view?.showFillClientFirstError()
        } else {
            view?.showFillClientFirstError()
        }

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

    override fun onHobbyClicked() {
        findEventDetailItemInList<EventDetailHobbyItem>()
            ?.let { eventDetailHobbyItem ->
                if (!eventDetailHobbyItem.isHobbyIncluded) {
                    view?.showIncludeHobbyConfirmationDialog()
                }
            }
    }

    //endregion

    //region EventDetailIncludeMemoryAttentionItemViewHolder.Callback


    //endregion

    //region EventDetailReportItemViewHolder.Callback

    override fun onReportClicked() {
        findEventDetailItemInList<EventDetailReportItem>()
            ?.let { eventDetailReportItem ->
                view?.showReportHint(
                    when (eventDetailReportItem.reportStatus) {
                        ReportStatus.UPLOADED ->  R.string.report_status_uploaded_hint
                        ReportStatus.READY ->  R.string.report_status_ready_hint
                        ReportStatus.SENT ->  R.string.report_status_sent_hint
                        else -> R.string.report_status_new_hint
                    }
                )
            }
    }

    //endregion

    //region EventDetailSendToLocationItemViewHolder.Callback

    override fun onSendToLocationClicked() {
        findEventDetailItemInList<EventDetailSendToLocationItem>()
            ?.let { eventDetailSendToLocationItem ->
                if (!eventDetailSendToLocationItem.isAlreadySent) {
                    view?.showSendToLocationConfirmationDialog()
                }
            }
    }

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
                logger.error("get available time slots error occurred", error)
                this.view?.showErrorSnackbar("get fiavailable time slots error occurred")
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
                view?.showErrorSnackbar("get create new event items models error occurred")
            })
            .let(::addDisposable)
    }

    private fun getViewModeEventItems(event: Event) {
        eventDetailInteractor.getStartModeEventDetailItems(event)
            .subscribe({ eventDetailItems ->
                eventDetailItemsList = eventDetailItems
                view?.setEventDetailItems(eventDetailItemsList)
            }, { error ->
                logger.error("get view mode event items models", error)
                view?.showErrorSnackbar("get view mode event items models error occurred")

            })
            .let(::addDisposable)
    }

    private fun getClient(clientId: String) {
        eventDetailInteractor.getClient(clientId)
            .subscribe({ client ->
                this.client = client
                if (eventDetailItemsList.isNotEmpty()) {
                    findEventDetailItemInList<EventDetailClientItem>()
                        ?.let { eventDetailClientItem ->
                            eventDetailClientItem.name = client?.name
                            eventDetailClientItem.phone = client?.phone
                            eventDetailClientItem.isLoading = false
                            view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailClientItem))
                        }
                }
            }, { error ->
                logger.error("get client error occurred", error)
                this.view?.showErrorSnackbar("get client error occurred")

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
            this.view?.showErrorSnackbar("create new event error occurred")

        })
            .let(::addDisposable)
    }

    private fun editEventForHobby() {
        event?.isHobbyIncluded = true
        eventDetailInteractor.editEvent(event ?: throw IllegalStateException("Try to edit event while event object is null"))
            .subscribe({
                findEventDetailItemInList<EventDetailHobbyItem>()
                    ?.let { eventDetailHobbyItem ->
                        eventDetailHobbyItem.isLoading = false
                        eventDetailHobbyItem.isHobbyIncluded = true
                        view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailHobbyItem))
                    }

            }, { error ->
                logger.error("edit event for hobby event error occurred", error)
                this.view?.showErrorSnackbar("edit event for hobby event error occurred")
                findEventDetailItemInList<EventDetailHobbyItem>()
                    ?.let { eventDetailHobbyItem ->
                        event?.isHobbyIncluded = false
                        eventDetailHobbyItem.isLoading = false
                        view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailHobbyItem))
                    }
            })
            .let(::addDisposable)
    }

    private fun sendReportToLocation() {
        eventDetailInteractor.sendReportToLocation(event?.report?.reportId ?: throw IllegalStateException("Try to send report to location while event is null"))
            .subscribe({
                findEventDetailItemInList<EventDetailSendToLocationItem>()
                    ?.let { eventDetailSendToLocationItem ->
                        eventDetailSendToLocationItem.isLoading = false
                        eventDetailSendToLocationItem.isAlreadySent = true
                        view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailSendToLocationItem))
                    }
            }, { error ->
                logger.error("send report to location error occurred", error)
                view?.showErrorSnackbar("send report to location error occurred")
                findEventDetailItemInList<EventDetailSendToLocationItem>()
                    ?.let { eventDetailSendToLocationItem ->
                        eventDetailSendToLocationItem.isLoading = false
                        eventDetailSendToLocationItem.isAlreadySent = false
                        view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailSendToLocationItem))
                    }
            })
            .let(::addDisposable)
    }

    private fun checkIsAllFieldsFilled() {
        if (client != null && child != null) {
            if (eventDetailItemsList.isNotEmpty()) {
                findEventDetailItemInList<EventDetailSubmitItem>()
                    ?.let { eventDetailSubmitItem ->
                        eventDetailSubmitItem.isAllFieldsFilled = true
                        view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailSubmitItem))
                    }
            }
        } else {
            if (eventDetailItemsList.isNotEmpty()) {
                findEventDetailItemInList<EventDetailSubmitItem>()
                    ?.let { eventDetailSubmitItem ->
                        eventDetailSubmitItem.isAllFieldsFilled = false
                        view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailSubmitItem))
                    }
            }
        }
    }

    private inline fun <reified T> findEventDetailItemInList(): T? =
        (eventDetailItemsList.firstOrNull { item -> item is T } as? T)

}