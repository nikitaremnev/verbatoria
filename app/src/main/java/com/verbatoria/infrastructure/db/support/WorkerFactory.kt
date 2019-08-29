package com.verbatoria.infrastructure.db.support

import io.reactivex.Scheduler

/**
 * @author n.remnev
 */

interface WorkerFactory {

    fun newWorker(worker: Scheduler.Worker): Scheduler.Worker

}