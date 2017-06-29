package com.verbatoria.utils;

import rx.Scheduler;
import rx.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Utils class that provide Rx Schedulers and appropriate Rx Transformers
 *
 * @author nikitaremnev
 */
public class RxSchedulers {

    public static Scheduler getMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    public static Scheduler getIOScheduler() {
        return Schedulers.io();
    }

    public static Scheduler getComputationScheduler() {
        return Schedulers.computation();
    }

    public static Scheduler getNewThreadScheduler() {
        return Schedulers.newThread();
    }

}
