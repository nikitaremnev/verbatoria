package com.verbatoria.business.schedule;

import com.verbatoria.business.schedule.datasource.IScheduleDataSource;

import rx.Observable;

/**
 * Интерфейс интерактора для расписания
 *
 * @author nikitaremnev
 */
public interface IScheduleInteractor {

    Observable<IScheduleDataSource> getSchedule();

}
