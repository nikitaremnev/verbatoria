package com.verbatoria.di.dashboard.calendar

import com.remnev.verbatoria.R
import com.verbatoria.business.dashboard.calendar.CalendarInteractorImpl
import com.verbatoria.business.dashboard.calendar.models.item.EventItemModel
import com.verbatoria.di.FragmentScope
import com.verbatoria.domain.dashboard.calendar.manager.CalendarManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.infrastructure.utils.ViewInflater
import com.verbatoria.ui.common.Adapter
import com.verbatoria.ui.common.ItemAdapter
import com.verbatoria.ui.dashboard.calendar.CalendarPresenter
import com.verbatoria.ui.dashboard.calendar.item.EventItemBinder
import com.verbatoria.ui.dashboard.calendar.item.EventItemViewHolderImpl
import dagger.Module
import dagger.Provides

/**
 * @author n.remnev
 */

@Module
class CalendarModule {

    @Provides
    @FragmentScope
    fun provideCalendarPresenter(
        calendarManager: CalendarManager,
        schedulersFactory: RxSchedulersFactory
    ): CalendarPresenter =
        CalendarPresenter(
            CalendarInteractorImpl(
                calendarManager,
                schedulersFactory
            )
        )

    @Provides
    @FragmentScope
    fun provideAdapter(
        presenter: CalendarPresenter
    ): Adapter =
        Adapter(
            listOf(
                ItemAdapter(
                    { it is EventItemModel },
                    {
                        EventItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_event, it),
                            presenter
                        )
                    },
                    EventItemBinder()
                )
            )
        )


}
