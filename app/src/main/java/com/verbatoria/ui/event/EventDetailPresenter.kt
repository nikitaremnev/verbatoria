package com.verbatoria.ui.event

import com.remnev.verbatoria.R
import com.verbatoria.business.event.EventDetailInteractor
import com.verbatoria.ui.base.BasePresenter
import com.verbatoria.ui.event.item.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

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
    EventDetailSubmitItemViewHolder.Callback {

    private val logger: Logger = LoggerFactory.getLogger("EventDetailPresenter")

    private var currentMode: EventDetailMode = eventDetailMode

    init {
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

    override fun onNavigationClicked() {
        view?.close()
    }

    //endregion

    //region EventDetailArchimedesItemViewHolder.Callback


    //endregion

    //region EventDetailChildItemViewHolder.Callback


    //endregion


    //region EventDetailClientItemViewHolder.Callback


    //endregion

    //region EventDetailDateItemViewHolder.Callback

    override fun onDateClicked() {

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

            }
            EventDetailMode.START -> {

            }
            EventDetailMode.EDIT -> {

            }
        }
    }

    //endregion



    private fun getCreateNewEventItems() {
        eventDetailInteractor.getCreateNewModeEventDetailItems()
            .subscribe({ eventDetailItems ->
                view?.setEventDetailItems(eventDetailItems)
            }, { error ->
                logger.error("get create new event items models", error)
            })
            .let(::addDisposable)
    }

    private fun getClient(clientId: String) {
//        eventDetailInteractor.getClient(clientId)
    }

}