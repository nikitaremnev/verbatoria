package com.verbatoria.business.schedule;

import com.verbatoria.business.schedule.datasource.IScheduleDataSource;
import com.verbatoria.business.schedule.datasource.ScheduleDataSource;
import com.verbatoria.data.network.request.ScheduleDeleteRequestModel;
import com.verbatoria.data.network.request.ScheduleItemRequestModel;
import com.verbatoria.data.network.request.ScheduleRequestModel;
import com.verbatoria.data.network.response.ScheduleItemResponseModel;
import com.verbatoria.data.repositories.schedule.IScheduleRepository;
import com.verbatoria.utils.DateUtils;
import com.verbatoria.utils.RxSchedulers;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;

import static com.verbatoria.utils.LocaleHelper.LOCALE_RU;

/**
 * Реализация интерактора для расписания
 *
 * @author nikitaremnev
 */
public class ScheduleInteractor implements IScheduleInteractor {

    private static final String TAG = ScheduleInteractor.class.getSimpleName();

    private IScheduleRepository mScheduleRepository;

    public ScheduleInteractor(IScheduleRepository scheduleRepository) {
        mScheduleRepository = scheduleRepository;
    }

    @Override
    public Observable<IScheduleDataSource> getSchedule() {
        IScheduleDataSource scheduleDataSource = new ScheduleDataSource();
        try {
            return mScheduleRepository.getSchedule(getAccessToken(),
                    DateUtils.toServerDateTimeWithoutConvertingString(scheduleDataSource.getWeekStart().getTime()),
                    DateUtils.toServerDateTimeWithoutConvertingString(scheduleDataSource.getWeekEnd().getTime()))
                    .map(scheduleResponseModel -> {
                        IScheduleDataSource hardScheduleDataSource = new ScheduleDataSource(Calendar.getInstance(new Locale(LOCALE_RU)));
                        for (ScheduleItemResponseModel scheduleItemResponseModel : scheduleResponseModel.getScheduleItems()) {
                            Date fromDate = null;
                            try {
                                fromDate = DateUtils.parseDateTime(scheduleItemResponseModel.getFromTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
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
    public Observable<IScheduleDataSource> getSchedulePreviousWeek(IScheduleDataSource currentScheduleDataSource) {
        try {
            return mScheduleRepository.getSchedule(getAccessToken(),
                    DateUtils.toServerDateTimeWithoutConvertingString(currentScheduleDataSource.getPreviousWeekStart().getTime()),
                    DateUtils.toServerDateTimeWithoutConvertingString(currentScheduleDataSource.getPreviousWeekFinish().getTime()))
                    .map(scheduleResponseModel -> {
                        Calendar calendar = currentScheduleDataSource.getOriginalCalendar();
                        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);
                        IScheduleDataSource hardScheduleDataSource = new ScheduleDataSource(calendar);
                        for (ScheduleItemResponseModel scheduleItemResponseModel : scheduleResponseModel.getScheduleItems()) {
                            Date fromDate = null;
                            try {
                                fromDate = DateUtils.parseDateTime(scheduleItemResponseModel.getFromTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
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
    public Observable<IScheduleDataSource> getScheduleNextWeek(IScheduleDataSource currentScheduleDataSource) {
        try {
            return mScheduleRepository.getSchedule(getAccessToken(),
                    DateUtils.toServerDateTimeWithoutConvertingString(currentScheduleDataSource.getNextWeekStart().getTime()),
                    DateUtils.toServerDateTimeWithoutConvertingString(currentScheduleDataSource.getNextWeekFinish().getTime()))
                    .map(scheduleResponseModel -> {
                        Calendar calendar = currentScheduleDataSource.getOriginalCalendar();
                        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 7);
                        IScheduleDataSource hardScheduleDataSource = new ScheduleDataSource(calendar);
                        for (ScheduleItemResponseModel scheduleItemResponseModel : scheduleResponseModel.getScheduleItems()) {
                            Date fromDate = null;
                            try {
                                fromDate = DateUtils.parseDateTime(scheduleItemResponseModel.getFromTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
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
    public Observable<IScheduleDataSource> saveSchedule(IScheduleDataSource scheduleDataSource, int weeksForwardCount) {
        try {
            return mScheduleRepository.saveSchedule(getAccessToken(), createScheduleRequestModel(scheduleDataSource, weeksForwardCount))
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

    @Override
    public Observable<Integer> deleteSchedule(IScheduleDataSource scheduleDataSource, int weeksForwardCount) {
        try {
            return mScheduleRepository.deleteSchedule(getAccessToken(), createScheduleDeleteRequestModel(scheduleDataSource, weeksForwardCount))
                    .map(scheduleItemResponseModels -> weeksForwardCount)
                    .subscribeOn(RxSchedulers.getNewThreadScheduler())
                    .observeOn(RxSchedulers.getMainThreadScheduler());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getAccessToken() {
        return "";
    }

    private ScheduleRequestModel createScheduleRequestModel(IScheduleDataSource scheduleDataSource, int weeksForwardCount) throws ParseException {
        return new ScheduleRequestModel()
                .setScheduleItems(createScheduleItemsRequestModel(scheduleDataSource.getItems(true, weeksForwardCount)));
    }

    private ScheduleDeleteRequestModel createScheduleDeleteRequestModel(IScheduleDataSource scheduleDataSource, int weeksForwardCount) throws ParseException {
        return new ScheduleDeleteRequestModel()
                .setFromTime(DateUtils.toServerDateTimeWithoutConvertingString(scheduleDataSource.getWeekStart().getTime()))
                .setToTime(DateUtils.toServerDateTimeWithoutConvertingString(scheduleDataSource.getDeleteBorder(weeksForwardCount).getTime()));
    }

    private List<ScheduleItemRequestModel> createScheduleItemsRequestModel(Map<Date, List<Date>> scheduleItems) throws ParseException {
        List<ScheduleItemRequestModel> scheduleItemRequestModelList = new ArrayList<>();
        Set<Date> keys = scheduleItems.keySet();
        Calendar calendar = Calendar.getInstance(new Locale(LOCALE_RU));
        Calendar helperCalendar = Calendar.getInstance(new Locale(LOCALE_RU));
        for (Date key : keys) {
            calendar.setTime(key);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            List<Date> subItems = scheduleItems.get(key);
            for (int i = 0; i < subItems.size(); i ++) {
                Date time = subItems.get(i);
                helperCalendar.setTime(time);
                int hour = helperCalendar.get(Calendar.HOUR_OF_DAY);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                Date start = calendar.getTime();
                calendar.set(Calendar.HOUR_OF_DAY, hour + 1);
                Date end = calendar.getTime();
                scheduleItemRequestModelList.add(new ScheduleItemRequestModel()
                        .setFromTime(DateUtils.toServerDateTimeWithoutConvertingString(start.getTime()))
                        .setToTime(DateUtils.toServerDateTimeWithoutConvertingString(end.getTime())));
            }
        }
        return scheduleItemRequestModelList;
    }
}
