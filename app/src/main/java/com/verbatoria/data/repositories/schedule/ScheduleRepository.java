package com.verbatoria.data.repositories.schedule;

import com.verbatoria.business.schedule.datasource.IScheduleDataSource;
import com.verbatoria.business.schedule.datasource.ScheduleDataSource;

import java.util.concurrent.Callable;

import rx.Observable;

/**
 * @author nikitaremnev
 */
public class ScheduleRepository implements IScheduleRepository {

    public ScheduleRepository() {

    }

    @Override
    public Observable<IScheduleDataSource> getSchedule(String accessToken) {
        return Observable.fromCallable((Callable<IScheduleDataSource>) () -> new ScheduleDataSource());
    }

}
