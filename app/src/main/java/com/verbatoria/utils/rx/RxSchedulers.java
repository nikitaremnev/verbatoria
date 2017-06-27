package com.verbatoria.utils.rx;

import rx.Scheduler;
import rx.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author nikitaremnev
 */
public class RxSchedulers implements IRxSchedulers {

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

    @Override
    public Scheduler getNewThreadScheduler() {
        return Schedulers.newThread();
    }

}
