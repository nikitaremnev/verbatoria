package com.verbatoria.data.repositories.schedule;

import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.request.ScheduleRequestModel;
import com.verbatoria.data.network.response.ScheduleResponseModel;

import rx.Observable;

/**
 * @author nikitaremnev
 */
public class ScheduleRepository implements IScheduleRepository {

    public ScheduleRepository() {

    }

    @Override
    public Observable<ScheduleResponseModel> getSchedule(String accessToken, String fromTime, String toTime) {
        return APIFactory.getAPIService().getSchedule(accessToken, fromTime, toTime);
    }

    @Override
    public Observable<ScheduleResponseModel> saveSchedule(String accessToken, ScheduleRequestModel scheduleRequestModel) {
        return APIFactory.getAPIService().addSchedule(accessToken, scheduleRequestModel);
    }

}
