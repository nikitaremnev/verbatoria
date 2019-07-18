package com.verbatoria.di.dashboard

import com.verbatoria.di.BaseInjector
import com.verbatoria.di.dashboard.calendar.CalendarComponent
import com.verbatoria.di.dashboard.info.InfoComponent
import com.verbatoria.di.dashboard.settings.SettingsComponent
import com.verbatoria.ui.dashboard.DashboardActivity
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [DashboardModule::class])
interface DashboardComponent : BaseInjector<DashboardActivity> {

    fun plusInfoComponent(): InfoComponent.Builder

    fun plusCalendarComponent(): CalendarComponent.Builder

    fun plusSettingsComponent(): SettingsComponent.Builder

    @Subcomponent.Builder
    interface Builder {

        fun build(): DashboardComponent

    }

}
