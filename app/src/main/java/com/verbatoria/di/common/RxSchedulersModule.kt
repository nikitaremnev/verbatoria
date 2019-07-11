package com.verbatoria.di.common

import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.infrastructure.rx.RxSchedulersFactoryImpl
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author n.remnev
 */

private const val IO_SCHEDULER = "io_scheduler"
private const val MAIN_SCHEDULER = "main_scheduler"

@Module
class RxSchedulersModule {

    @Provides
    @Named(IO_SCHEDULER)
    fun provideIoScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Named(MAIN_SCHEDULER)
    fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    fun provideRxSchedulersFactory(
        @Named(IO_SCHEDULER) ioScheduler: Scheduler,
        @Named(MAIN_SCHEDULER) mainScheduler: Scheduler
    ): RxSchedulersFactory =
        RxSchedulersFactoryImpl(
            ioScheduler,
            mainScheduler
        )

}