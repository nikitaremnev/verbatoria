package com.verbatoria.business.schedule;

import com.verbatoria.business.schedule.datasource.IScheduleDataSource;
import com.verbatoria.business.schedule.datasource.ScheduleDataSource;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.response.ScheduleItemResponseModel;
import com.verbatoria.data.repositories.schedule.IScheduleRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.utils.DateUtils;
import com.verbatoria.utils.RxSchedulers;

import java.text.ParseException;
import java.util.Date;

import okhttp3.ResponseBody;
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

    private String getAccessToken() {
        TokenModel tokenModel = mTokenRepository.getToken();
        return tokenModel.getAccessToken();
    }

    @Override
    public Observable<IScheduleDataSource> getSchedule() {
        IScheduleDataSource scheduleDataSource = new ScheduleDataSource();
        try {
            return mScheduleRepository.getScheduleFromNetwork(getAccessToken(),
                    DateUtils.toServerDateTimeWithoutConvertingString(scheduleDataSource.getWeekStart().getTime()),
                    DateUtils.toServerDateTimeWithoutConvertingString(scheduleDataSource.getWeekEnd().getTime()))
                    .map(scheduleResponseModel -> {
                        for (ScheduleItemResponseModel scheduleItemResponseModel : scheduleResponseModel.getScheduleItems()) {
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
    public Observable<ResponseBody> saveSchedule(IScheduleDataSource scheduleDataSource) {
        return "";
    }
}
