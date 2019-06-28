package com.verbatoria.infrastructure.rx

import io.reactivex.Scheduler

/**
 * @author n.remnev
 */

interface RxSchedulersFactory {

    val io: Scheduler

    val main: Scheduler

}

class RxSchedulersFactoryImpl(
    override val io: Scheduler,
    override val main: Scheduler
) : RxSchedulersFactory