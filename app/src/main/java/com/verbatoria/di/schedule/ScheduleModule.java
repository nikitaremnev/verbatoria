package com.verbatoria.di.schedule;

import com.verbatoria.business.schedule.IScheduleInteractor;
import com.verbatoria.business.schedule.ScheduleInteractor;
import com.verbatoria.data.repositories.schedule.IScheduleRepository;
import com.verbatoria.data.repositories.schedule.ScheduleRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.presentation.schedule.presenter.ISchedulePresenter;
import com.verbatoria.presentation.schedule.presenter.SchedulePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Модуль даггера для расписания
 *
 * @author nikitaremnev
 */
@Module
public class ScheduleModule {

    @Provides
    @ScheduleScope
    IScheduleRepository provideScheduleRepository() {
        return new ScheduleRepository();
    }

    @Provides
    @ScheduleScope
    IScheduleInteractor provideScheduleInteractor(IScheduleRepository scheduleRepository, ITokenRepository tokenRepository) {
        return new ScheduleInteractor(scheduleRepository, tokenRepository);
    }

    @Provides
    @ScheduleScope
    ISchedulePresenter provideSchedulePresenter(IScheduleInteractor scheduleInteractor) {
        return new SchedulePresenter(scheduleInteractor);
    }

}
