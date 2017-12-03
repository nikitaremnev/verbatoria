package com.verbatoria.business.schedule;

import com.verbatoria.business.schedule.datasource.IScheduleDataSource;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.repositories.schedule.IScheduleRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.utils.RxSchedulers;

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
        return mScheduleRepository.getSchedule(getAccessToken())
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }
}
