package com.verbatoria.data.repositories.schedule;

import com.verbatoria.data.network.request.ScheduleRequestModel;
import com.verbatoria.data.network.response.ScheduleResponseModel;

import rx.Observable;

/**
 * @author nikitaremnev
 */
public interface IScheduleRepository {

    Observable<ScheduleResponseModel> getSchedule(String accessToken, String fromTime, String toTime);

    Observable<ScheduleResponseModel> saveSchedule(String accessToken, ScheduleRequestModel scheduleRequestModel);


}
