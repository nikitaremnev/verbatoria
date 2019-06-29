package com.verbatoria.utils;


import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
