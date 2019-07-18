package com.verbatoria.di.dashboard.calendar

import com.remnev.verbatoria.R
import com.verbatoria.business.dashboard.calendar.CalendarInteractorImpl
import com.verbatoria.business.dashboard.calendar.models.EmptyItemModel
import com.verbatoria.di.FragmentScope
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.infrastructure.utils.ViewInflater
import com.verbatoria.ui.common.Adapter
import com.verbatoria.ui.common.ItemAdapter
import com.verbatoria.ui.dashboard.calendar.CalendarPresenter
import com.verbatoria.ui.dashboard.calendar.item.EmptyItemBinder
import com.verbatoria.ui.dashboard.calendar.item.EmptyItemViewHolder
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
        schedulersFactory: RxSchedulersFactory
    ): CalendarPresenter =
        CalendarPresenter(
            CalendarInteractorImpl(
                schedulersFactory
            )
        )

    @Provides
    @FragmentScope
    fun provideAdapter(): Adapter =
        Adapter(
            listOf(
                ItemAdapter(
                    { it is EmptyItemModel },
                    {
                        EmptyItemViewHolder(
                            ViewInflater.inflate(R.layout.item_empty, it)
                        )
                    },
                    EmptyItemBinder()
                )
            )
        )


}
