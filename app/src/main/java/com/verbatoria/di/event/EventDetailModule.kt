package com.verbatoria.di.event

import com.remnev.verbatoria.R
import com.verbatoria.business.event.*
import com.verbatoria.business.event.models.item.*
import com.verbatoria.domain.client.manager.ClientManager
import com.verbatoria.domain.dashboard.calendar.manager.CalendarManager
import com.verbatoria.domain.dashboard.calendar.model.Event
import com.verbatoria.domain.dashboard.info.manager.InfoManager
import com.verbatoria.domain.report.manager.ReportManager
import com.verbatoria.domain.schedule.manager.ScheduleManager
import com.verbatoria.domain.submit.SubmitManager
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
                    { it is EventDetailArchimedesItem },
                    {
                        EventDetailArchimedesItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_event_detail_archimedes, it)
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
                    { it is EventDetailIncludeAttentionMemoryItem },
                    {
                        EventDetailIncludeAttentionMemoryItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_event_detail_include_memory_attention, it), eventDetailPresenter
                        )
                    },
                    EventDetailIncludeAttentionMemoryItemBinder()
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
        infoManager: InfoManager,
        submitManager: SubmitManager,
        rxSchedulersFactory: RxSchedulersFactory
    ): EventDetailPresenter =
        EventDetailPresenter(
            EventDetailMode.valueOf(eventDetailModeOrdinal),
            event,
            EventDetailInteractorImpl(calendarManager, scheduleManager, clientManager, reportManager, infoManager, submitManager, rxSchedulersFactory)
        )

}