package com.verbatoria.di.schedule

import com.verbatoria.ui.schedule.SchedulePresenter
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class ScheduleModule {

    @Provides
    @Reusable
    fun provideSchedulePresenter(): SchedulePresenter =
        SchedulePresenter()

}