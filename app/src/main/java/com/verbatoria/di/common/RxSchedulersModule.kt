package com.verbatoria.di.common

import com.verbatoria.infrastructure.db.support.DatabaseScheduler
import com.verbatoria.infrastructure.db.support.DatabaseThreadFactory
import com.verbatoria.infrastructure.db.support.DatabaseWorkerFactory
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
private const val DATABASE_SCHEDULER = "database_scheduler"

private const val DEFAULT_THREAD_COUNT = 8
private const val DEFAULT_CORE_THREAD_COUNT = 4

@Module
class RxSchedulersModule {

    @Provides
    @Named(IO_SCHEDULER)
    fun provideIoScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Named(MAIN_SCHEDULER)
    fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Named(DATABASE_SCHEDULER)
    fun provideDatabaseScheduler(
        threadFactory: DatabaseThreadFactory,
        workerFactory: DatabaseWorkerFactory
    ): Scheduler =
        DatabaseScheduler(
            threadFactory,
            workerFactory,
            DEFAULT_CORE_THREAD_COUNT,
            DEFAULT_THREAD_COUNT
        )

    @Provides
    @Singleton
    fun provideRxSchedulersFactory(
        @Named(IO_SCHEDULER) ioScheduler: Scheduler,
        @Named(MAIN_SCHEDULER) mainScheduler: Scheduler,
        @Named(DATABASE_SCHEDULER) databaseScheduler: Scheduler
    ): RxSchedulersFactory =
        RxSchedulersFactoryImpl(
            ioScheduler,
            mainScheduler,
            databaseScheduler
        )

}