package com.verbatoria.di.event

import com.verbatoria.di.BaseInjector
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

        fun build(): EventDetailComponent

    }

}