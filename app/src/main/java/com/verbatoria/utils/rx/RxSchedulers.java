package com.verbatoria.utils.rx;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author nikitaremnev
 */
public class RxSchedulers extends RxSchedulersAbs {

    @Override
    public Scheduler getMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler getIOScheduler() {
        return Schedulers.io();
    }

    @Override
    public Scheduler getComputationScheduler() {
        return Schedulers.computation();
    }

}
