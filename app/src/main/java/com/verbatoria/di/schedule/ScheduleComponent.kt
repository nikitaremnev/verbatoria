package com.verbatoria.di.schedule

import com.verbatoria.di.BaseInjector
import com.verbatoria.ui.schedule.ScheduleActivity
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [ScheduleModule::class])
interface ScheduleComponent : BaseInjector<ScheduleActivity> {

    @Subcomponent.Builder
    interface Builder {

        fun build(): ScheduleComponent

    }

}