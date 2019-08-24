package com.verbatoria.di.schedule

import com.verbatoria.business.schedule.ScheduleInteractorImpl
import com.verbatoria.domain.schedule.ScheduleManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
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
    fun provideSchedulePresenter(
        scheduleManager: ScheduleManager,
        schedulersFactory: RxSchedulersFactory
    ): SchedulePresenter =
        SchedulePresenter(
            ScheduleInteractorImpl(
                scheduleManager,
                schedulersFactory
            )
        )

}