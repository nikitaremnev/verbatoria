package com.verbatoria.infrastructure.database.common.support

import io.reactivex.Scheduler
import io.reactivex.internal.schedulers.ExecutorScheduler
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @author n.remnev
 */

private const val CORE_THREAD_COUNT = 4
private const val THREAD_COUNT = 8
private const val THREAD_KEEP_ALIVE_TIME = 5L

class DatabaseScheduler(
    threadFactory: ThreadFactory,
    private val workerFactory: WorkerFactory,
    coreThreadCount: Int = CORE_THREAD_COUNT,
    threadCount: Int = THREAD_COUNT
) : Scheduler() {

    val executor = ThreadPoolExecutor(
        coreThreadCount, threadCount, THREAD_KEEP_ALIVE_TIME,
        TimeUnit.MINUTES, LinkedBlockingQueue<Runnable>(), threadFactory
    )

    val scheduler: ExecutorScheduler = ExecutorScheduler(executor, true)

    override fun createWorker(): Worker = workerFactory.newWorker(scheduler.createWorker())

}