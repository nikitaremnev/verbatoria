package com.verbatoria.di.schedule;

import com.verbatoria.presentation.schedule.view.ScheduleActivity;

import dagger.Subcomponent;

/**
 * Компонент Даггера для расписания
 *
 * @author nikitaremnev
 */
@Subcomponent(modules = {ScheduleModule.class})
@ScheduleScope
public interface ScheduleComponent {

    void inject(ScheduleActivity scheduleActivity);

}
