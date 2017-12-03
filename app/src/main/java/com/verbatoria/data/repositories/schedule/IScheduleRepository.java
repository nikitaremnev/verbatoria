package com.verbatoria.data.repositories.schedule;

import com.verbatoria.business.schedule.datasource.IScheduleDataSource;

import rx.Observable;

/**
 * @author nikitaremnev
 */
public interface IScheduleRepository {

    Observable<IScheduleDataSource> getSchedule(String accessToken);

}
