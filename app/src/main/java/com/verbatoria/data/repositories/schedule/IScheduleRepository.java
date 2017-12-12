package com.verbatoria.data.repositories.schedule;

import com.verbatoria.data.network.request.ScheduleDeleteRequestModel;
import com.verbatoria.data.network.request.ScheduleRequestModel;
import com.verbatoria.data.network.response.ScheduleItemResponseModel;
import com.verbatoria.data.network.response.ScheduleResponseModel;

import java.util.List;

import rx.Observable;

/**
 * @author nikitaremnev
 */
public interface IScheduleRepository {

    Observable<ScheduleResponseModel> getSchedule(String accessToken, String fromTime, String toTime);

    Observable<List<ScheduleItemResponseModel>> saveSchedule(String accessToken, ScheduleRequestModel scheduleRequestModel);

    Observable<List<ScheduleItemResponseModel>> deleteSchedule(String accessToken, ScheduleDeleteRequestModel scheduleDeleteRequestModel);

}
