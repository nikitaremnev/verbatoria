package com.verbatoria.utils.rx;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * @author nikitaremnev
 */
public class RxSchedulersTest extends RxSchedulersAbs {

    @Override
    public Scheduler getMainThreadScheduler() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler getIOScheduler() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler getComputationScheduler() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler getNewThreadScheduler() {
        return Schedulers.immediate();
    }

}
