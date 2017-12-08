package com.verbatoria.data.repositories.schedule;

import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.request.ScheduleRequestModel;
import com.verbatoria.data.network.response.ScheduleItemResponseModel;
import com.verbatoria.data.network.response.ScheduleResponseModel;

import java.util.List;

import rx.Observable;

/**
 * @author nikitaremnev
 */
public class ScheduleRepository implements IScheduleRepository {

    private static final int SCHEDULE_ITEMS_PER_PAGE = 7 * 14;

    public ScheduleRepository() {

    }

    @Override
    public Observable<ScheduleResponseModel> getSchedule(String accessToken, String fromTime, String toTime) {
        return APIFactory.getAPIService().getSchedule(accessToken, fromTime, toTime, SCHEDULE_ITEMS_PER_PAGE);
    }

    @Override
    public Observable<List<ScheduleItemResponseModel>> saveSchedule(String accessToken, ScheduleRequestModel scheduleRequestModel) {
        return APIFactory.getAPIService().saveSchedule(accessToken, scheduleRequestModel);
    }

}
