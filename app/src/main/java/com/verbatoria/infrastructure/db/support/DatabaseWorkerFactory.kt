package com.verbatoria.infrastructure.db.support

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author n.remnev
 */

class DatabaseWorkerFactory @Inject constructor() : WorkerFactory {

    override fun newWorker(worker: Scheduler.Worker) = DatabaseWorker(worker)

    class DatabaseWorker(
            private val worker: Scheduler.Worker
    ) : Scheduler.Worker() {
        override fun schedule(action: Runnable): Disposable = schedule(action, 0, TimeUnit.MILLISECONDS)

        override fun schedule(action: Runnable, delay: Long, unit: TimeUnit): Disposable =
                worker.schedule(action, delay, unit)

        override fun isDisposed(): Boolean = worker.isDisposed

        override fun dispose() {
            worker.dispose()
        }
    }

}