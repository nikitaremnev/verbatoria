package com.verbatoria.data.repositories.schedule;

import com.verbatoria.data.network.response.ScheduleResponseModel;

import rx.Observable;

/**
 * @author nikitaremnev
 */
public interface IScheduleRepository {

    Observable<ScheduleResponseModel> getScheduleFromNetwork(String accessToken, String fromTime, String toTime);

}
