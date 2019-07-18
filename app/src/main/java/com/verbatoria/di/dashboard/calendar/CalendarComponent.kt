package com.verbatoria.di.dashboard.calendar

import com.verbatoria.di.BaseInjector
import com.verbatoria.di.FragmentScope
import com.verbatoria.ui.dashboard.calendar.CalendarFragment
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [CalendarModule::class])
@FragmentScope
interface CalendarComponent : BaseInjector<CalendarFragment> {

    @Subcomponent.Builder
    interface Builder {

        fun build(): CalendarComponent

    }

}
