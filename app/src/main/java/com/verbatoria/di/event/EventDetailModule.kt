package com.verbatoria.di.event

import com.remnev.verbatoria.R
import com.verbatoria.business.event.*
import com.verbatoria.business.event.models.item.*
import com.verbatoria.domain.client.ClientManager
import com.verbatoria.domain.dashboard.calendar.CalendarManager
import com.verbatoria.domain.dashboard.calendar.Event
import com.verbatoria.domain.report.ReportManager
import com.verbatoria.domain.schedule.ScheduleManager
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
                            ViewInflater.inflate(R.layout.item_event_detail_archimedes, it), eventDetailPresenter
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
                    { it is EventDetailTimeItem },
                    {
                        EventDetailTimeItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_event_detail_date, it), eventDetailPresenter
                        )
                    },
                    EventDetailTimeItemBinder()
                ),
                ItemAdapter(
                    { it is EventDetailHobbyItem },
                    {
                        EventDetailHobbyItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_event_detail_hobby, it), eventDetailPresenter
                        )
                    },
                    EventDetailHobbyItemBinder()
                ),
                ItemAdapter(
                    { it is EventDetailIncludeMemoryAttentionItem },
                    {
                        EventDetailIncludeMemoryAttentionItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_event_detail_include_memory_attention, it), eventDetailPresenter
                        )
                    },
                    EventDetailIncludeMemoryAttentionItemBinder()
                ),
                ItemAdapter(
                    { it is EventDetailReportItem },
                    {
                        EventDetailReportItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_event_detail_report, it), eventDetailPresenter
                        )
                    },
                    EventDetailReportItemBinder()
                ),
                ItemAdapter(
                    { it is EventDetailSendToLocationItem },
                    {
                        EventDetailSendToLocationItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_event_detail_send_to_location, it), eventDetailPresenter
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
        eventDetailModeOrdinal: Int,
        event: Event?,
        calendarManager: CalendarManager,
        scheduleManager: ScheduleManager,
        clientManager: ClientManager,
        reportManager: ReportManager,
        rxSchedulersFactory: RxSchedulersFactory
    ): EventDetailPresenter =
        EventDetailPresenter(
            EventDetailMode.valueOf(eventDetailModeOrdinal),
            event,
            EventDetailInteractorImpl(calendarManager, scheduleManager, clientManager, reportManager, rxSchedulersFactory)
        )

}