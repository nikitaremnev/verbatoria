package com.verbatoria.di.schedule

import com.verbatoria.business.schedule.ScheduleInteractorImpl
import com.verbatoria.infrastructure.retrofit.EndpointsRegister
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
        endpointsRegister: EndpointsRegister,
        schedulersFactory: RxSchedulersFactory
    ): SchedulePresenter =
        SchedulePresenter(
            ScheduleInteractorImpl(
                endpointsRegister.scheduleEndpoint,
                schedulersFactory
            )
        )

}