package com.verbatoria.ui.event

import android.app.DatePickerDialog
import android.widget.DatePicker
import com.remnev.verbatoria.R
import com.verbatoria.domain.child.Child
import com.verbatoria.business.event.EventDetailInteractor
import com.verbatoria.domain.client.Client
import com.verbatoria.business.event.models.item.EventDetailChildItem
import com.verbatoria.business.event.models.item.EventDetailClientItem
import com.verbatoria.business.event.models.item.EventDetailItem
import com.verbatoria.ui.base.BasePresenter
import com.verbatoria.ui.event.item.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author n.remnev
 */

class EventDetailPresenter(
    eventDetailMode: EventDetailMode,
    private val eventDetailInteractor: EventDetailInteractor
) : BasePresenter<EventDetailView>(),
    EventDetailView.Callback,
    EventDetailArchimedesItemViewHolder.Callback,
    EventDetailChildItemViewHolder.Callback,
    EventDetailClientItemViewHolder.Callback,
    EventDetailDateItemViewHolder.Callback,
    EventDetailHobbyItemViewHolder.Callback,
    EventDetailIncludeMemoryAttentionItemViewHolder.Callback,
    EventDetailReportItemViewHolder.Callback,
    EventDetailSendToLocationItemViewHolder.Callback,
    EventDetailSubmitItemViewHolder.Callback,
    DatePickerDialog.OnDateSetListener {

    private val logger: Logger = LoggerFactory.getLogger("EventDetailPresenter")

    private var currentMode: EventDetailMode = eventDetailMode

    private var eventDetailItemsList: List<EventDetailItem> = emptyList()

    private var client: Client? = null
    private var child: Child? = null

    init {
        getClient("13055")
//        if (currentMode.isCreateNew()) {
//            getCreateNewEventItems()
//        }
    }

    override fun onAttachView(view: EventDetailView) {
        super.onAttachView(view)

        if (currentMode.isCreateNew()) {
            getCreateNewEventItems()
            view.setTitle(R.string.event_detail_new)
        }
    }

    //region EventDetailView.Callback

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

    //region EventDetailDateItemViewHolder.Callback

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
            .subscribe({ availableIntervals ->
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
                            view?.updateEventDetailItem(eventDetailItemsList.indexOf(eventDetailClientItem))
                        }
                }
            }, { error ->
                logger.error("get create new event items models", error)
            })
            .let(::addDisposable)
    }

    private fun createNewEvent() {

    }
}