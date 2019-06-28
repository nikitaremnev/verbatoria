package com.verbatoria.infrastructure.database.common.support

import io.reactivex.Scheduler

/**
 * @author n.remnev
 */

interface WorkerFactory {

    fun newWorker(worker: Scheduler.Worker): Scheduler.Worker

}