package com.verbatoria.di.event

import com.remnev.verbatoria.R
import com.verbatoria.business.event.*
import com.verbatoria.business.event.models.item.*
import com.verbatoria.domain.client.ClientManager
import com.verbatoria.domain.dashboard.info.InfoManager
import com.verbatoria.domain.dashboard.info.InfoRepository
import com.verbatoria.infrastructure.retrofit.EndpointsRegister
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.infrastructure.utils.ViewInflater
import com.verbatoria.ui.common.Adapter
import com.verbatoria.ui.common.ItemAdapter
import com.verbatoria.ui.event.EventDetailMode
import com.verbatoria.ui.event.EventDetailPresenter
import com.verbatoria.ui.event.item.*
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class EventDetailModule {

    @Provides
    @Reusable
    fun provideAdapter(
        eventDetailPresenter: EventDetailPresenter
    ): Adapter =
        Adapter(
            listOf(
                ItemAdapter(
                    { it is EventDetailHeaderItem },
                    {
                        EventDetailHeaderItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_event_detail_header, it)
                        )
                    },
                    EventDetailHeaderItemBinder()
                ),
                ItemAdapter(
                    { it is EventDetailArchimedesItem },
                    {
                        EventDetailArchimedesItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_settings, it), eventDetailPresenter
                        )
                    },
                    EventDetailArchimedesItemBinder()
                ),
                ItemAdapter(
                    { it is EventDetailChildItem },
                    {
                        EventDetailChildItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_event_detail_child, it), eventDetailPresenter
                        )
                    },
                    EventDetailChildItemBinder()
                ),
                ItemAdapter(
                    { it is EventDetailClientItem },
                    {
                        EventDetailClientItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_event_detail_client, it), eventDetailPresenter
                        )
                    },
                    EventDetailClientItemBinder()
                ),
                ItemAdapter(
                    { it is EventDetailDateItem },
                    {
                        EventDetailDateItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_event_detail_date, it), eventDetailPresenter
                        )
                    },
                    EventDetailDateItemBinder()
                ),
                ItemAdapter(
                    { it is EventDetailHobbyItem },
                    {
                        EventDetailHobbyItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_settings, it), eventDetailPresenter
                        )
                    },
                    EventDetailHobbyItemBinder()
                ),
                ItemAdapter(
                    { it is EventDetailIncludeMemoryAttentionItem },
                    {
                        EventDetailIncludeMemoryAttentionItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_settings, it), eventDetailPresenter
                        )
                    },
                    EventDetailIncludeMemoryAttentionItemBinder()
                ),
                ItemAdapter(
                    { it is EventDetailReportItem },
                    {
                        EventDetailReportItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_settings, it), eventDetailPresenter
                        )
                    },
                    EventDetailReportItemBinder()
                ),
                ItemAdapter(
                    { it is EventDetailSendToLocationItem },
                    {
                        EventDetailSendToLocationItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_settings, it), eventDetailPresenter
                        )
                    },
                    EventDetailSendToLocationItemBinder()
                ),
                ItemAdapter(
                    { it is EventDetailSubmitItem },
                    {
                        EventDetailSubmitItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_event_detail_submit, it), eventDetailPresenter
                        )
                    },
                    EventDetailSubmitItemBinder()
                )
            )
        )

    @Provides
    @Reusable
    fun provideEventDetailPresenter(
        endpointsRegister: EndpointsRegister,
        eventDetailModeOrdinal: Int,
        clientManager: ClientManager,
        infoManager: InfoManager,
        rxSchedulersFactory: RxSchedulersFactory
    ): EventDetailPresenter =
        EventDetailPresenter(
            EventDetailMode.valueOf(eventDetailModeOrdinal),
            EventDetailInteractorImpl(endpointsRegister.eventEndpoint, clientManager, infoManager, rxSchedulersFactory)
        )

}