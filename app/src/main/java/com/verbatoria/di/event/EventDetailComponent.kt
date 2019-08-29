package com.verbatoria.di.event

import com.verbatoria.di.BaseInjector
import com.verbatoria.domain.dashboard.calendar.model.Event
import com.verbatoria.ui.event.EventDetailActivity
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [EventDetailModule::class])
interface EventDetailComponent : BaseInjector<EventDetailActivity> {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun eventDetailMode(eventDetailModeOrdinal: Int): Builder

        @BindsInstance
        fun event(event: Event?): Builder

        fun build(): EventDetailComponent

    }

}