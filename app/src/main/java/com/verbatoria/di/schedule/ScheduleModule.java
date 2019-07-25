package com.verbatoria.di.schedule;

import com.verbatoria.business.schedule.IScheduleInteractor;
import com.verbatoria.business.schedule.ScheduleInteractor;
import com.verbatoria.data.repositories.schedule.IScheduleRepository;
import com.verbatoria.data.repositories.schedule.ScheduleRepository;
import com.verbatoria.ui.schedule.presenter.ISchedulePresenter;
import com.verbatoria.ui.schedule.presenter.SchedulePresenter;

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
    IScheduleInteractor provideScheduleInteractor(IScheduleRepository scheduleRepository) {
        return new ScheduleInteractor(scheduleRepository);
    }

    @Provides
    @ScheduleScope
    ISchedulePresenter provideSchedulePresenter(IScheduleInteractor scheduleInteractor) {
        return new SchedulePresenter(scheduleInteractor);
    }

}
