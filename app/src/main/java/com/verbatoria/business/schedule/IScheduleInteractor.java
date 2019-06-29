package com.verbatoria.business.schedule;

import com.verbatoria.business.schedule.datasource.IScheduleDataSource;

import io.reactivex.Observable;

/**
 * Интерфейс интерактора для расписания
 *
 * @author nikitaremnev
 */
public interface IScheduleInteractor {

    Observable<IScheduleDataSource> getSchedule();

    Observable<IScheduleDataSource> getScheduleNextWeek(IScheduleDataSource scheduleDataSource);

    Observable<IScheduleDataSource> getSchedulePreviousWeek(IScheduleDataSource scheduleDataSource);

    Observable<IScheduleDataSource> saveSchedule(IScheduleDataSource scheduleDataSource, int weeksForwardCount);

    Observable<Integer> deleteSchedule(IScheduleDataSource scheduleDataSource,  int weeksForwardCount);
}
