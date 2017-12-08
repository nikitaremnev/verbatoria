package com.verbatoria.business.schedule;

import android.util.Log;

import com.verbatoria.business.schedule.datasource.IScheduleDataSource;
import com.verbatoria.business.schedule.datasource.ScheduleDataSource;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.request.ScheduleItemRequestModel;
import com.verbatoria.data.network.request.ScheduleRequestModel;
import com.verbatoria.data.network.response.ScheduleItemResponseModel;
import com.verbatoria.data.repositories.schedule.IScheduleRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.utils.DateUtils;
import com.verbatoria.utils.RxSchedulers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rx.Observable;

/**
 * Реализация интерактора для расписания
 *
 * @author nikitaremnev
 */
public class ScheduleInteractor implements IScheduleInteractor {

    private static final String TAG = ScheduleInteractor.class.getSimpleName();

    private IScheduleRepository mScheduleRepository;
    private ITokenRepository mTokenRepository;

    public ScheduleInteractor(IScheduleRepository scheduleRepository, ITokenRepository tokenRepository) {
        mScheduleRepository = scheduleRepository;
        mTokenRepository = tokenRepository;
    }

    @Override
    public Observable<IScheduleDataSource> getSchedule() {
        IScheduleDataSource scheduleDataSource = new ScheduleDataSource();
        try {
            return mScheduleRepository.getSchedule(getAccessToken(),
                    DateUtils.toServerDateTimeWithoutConvertingString(scheduleDataSource.getWeekStart().getTime()),
                    DateUtils.toServerDateTimeWithoutConvertingString(scheduleDataSource.getWeekEnd().getTime()))
                    .map(scheduleResponseModel -> {
                        IScheduleDataSource hardScheduleDataSource = new ScheduleDataSource(true);
                        for (ScheduleItemResponseModel scheduleItemResponseModel : scheduleResponseModel.getScheduleItems()) {
                            Date fromDate = null;
                            try {
                                fromDate = DateUtils.parseDateTime(scheduleItemResponseModel.getFromTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.e("test", "fromDate: " + fromDate.toString());
                            hardScheduleDataSource.setWorkingInterval(fromDate);
                        }
                        return hardScheduleDataSource;
                    })
                    .subscribeOn(RxSchedulers.getNewThreadScheduler())
                    .observeOn(RxSchedulers.getMainThreadScheduler());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Observable<IScheduleDataSource> saveSchedule(IScheduleDataSource scheduleDataSource) {
        try {
            return mScheduleRepository.saveSchedule(getAccessToken(), createScheduleRequestModel(scheduleDataSource))
                    .map(scheduleItemResponseModels -> {
                        for (ScheduleItemResponseModel scheduleItemResponseModel : scheduleItemResponseModels) {
                            Date fromDate = null;
                            try {
                                fromDate = DateUtils.parseDateTime(scheduleItemResponseModel.getFromTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            scheduleDataSource.setWorkingInterval(fromDate);
                        }
                        return scheduleDataSource;
                    })
                    .subscribeOn(RxSchedulers.getNewThreadScheduler())
                    .observeOn(RxSchedulers.getMainThreadScheduler());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getAccessToken() {
        TokenModel tokenModel = mTokenRepository.getToken();
        return tokenModel.getAccessToken();
    }

    private ScheduleRequestModel createScheduleRequestModel(IScheduleDataSource scheduleDataSource) throws ParseException {
        return new ScheduleRequestModel()
                .setScheduleItems(createScheduleItemsRequestModel(scheduleDataSource.getItems(true)));
    }

    private List<ScheduleItemRequestModel> createScheduleItemsRequestModel(Map<Date, List<Date>> scheduleItems) throws ParseException {
        List<ScheduleItemRequestModel> scheduleItemRequestModelList = new ArrayList<>();
        Set<Date> keys = scheduleItems.keySet();
        Calendar calendar = Calendar.getInstance();
        Calendar helperCalendar = Calendar.getInstance();
        for (Date key : keys) {

            calendar.setTime(key);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Log.e("test", "key: " + key);
            List<Date> subItems = scheduleItems.get(key);
            for (int i = 0; i < subItems.size(); i ++) {
                Date time = subItems.get(i);
                Log.e("test", "item: " + time);
                helperCalendar.setTime(time);
                int hour = helperCalendar.get(Calendar.HOUR_OF_DAY);
                Log.e("test", "hour: " + hour);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                Date start = calendar.getTime();
                Log.e("test", "start: " + start.toString());
                calendar.set(Calendar.HOUR_OF_DAY, hour + 1);
                Date end = calendar.getTime();
                Log.e("test", "end: " + end.toString());

                Log.e("test", "start.getTime(): " + DateUtils.toServerDateTimeWithoutConvertingString(start.getTime()));
                Log.e("test", "end.getTime(): " + DateUtils.toServerDateTimeWithoutConvertingString(end.getTime()));
                scheduleItemRequestModelList.add(new ScheduleItemRequestModel()
                        .setFromTime(DateUtils.toServerDateTimeWithoutConvertingString(start.getTime()))
                        .setToTime(DateUtils.toServerDateTimeWithoutConvertingString(end.getTime())));
            }
        }
        return scheduleItemRequestModelList;
    }
}
