package com.verbatoria.di.dashboard.calendar

import com.remnev.verbatoria.R
import com.verbatoria.business.dashboard.calendar.CalendarInteractorImpl
import com.verbatoria.business.dashboard.calendar.models.EventItemModel
import com.verbatoria.di.FragmentScope
import com.verbatoria.domain.dashboard.calendar.CalendarRepository
import com.verbatoria.infrastructure.retrofit.EndpointsRegister
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
        endpointsRegister: EndpointsRegister,
        calendarRepository: CalendarRepository,
        schedulersFactory: RxSchedulersFactory
    ): CalendarPresenter =
        CalendarPresenter(
            CalendarInteractorImpl(
                endpointsRegister.calendarEndpoint,
                calendarRepository,
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
