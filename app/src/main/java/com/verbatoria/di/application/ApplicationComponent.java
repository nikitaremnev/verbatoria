package com.verbatoria.di.application;

import com.verbatoria.di.calendar.CalendarComponent;
import com.verbatoria.di.calendar.CalendarModule;
import com.verbatoria.di.dashboard.DashboardComponent;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.di.late_send.LateSendComponent;
import com.verbatoria.di.late_send.LateSendModule;
import com.verbatoria.di.login.LoginComponent;
import com.verbatoria.di.login.LoginModule;
import com.verbatoria.di.schedule.ScheduleComponent;
import com.verbatoria.di.schedule.ScheduleModule;
import com.verbatoria.di.session.SessionComponent;
import com.verbatoria.di.session.SessionModule;
import com.verbatoria.di.token.TokenComponentInjects;
import com.verbatoria.di.token.TokenModule;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Компонент Даггера для модуля контекста
 *
 * @author nikitaremnev
 */
@Singleton
@Component(modules = {ApplicationModule.class, TokenModule.class})
public interface ApplicationComponent extends ApplicationComponentInjects, TokenComponentInjects {

    LoginComponent addModule(LoginModule loginModule);

    DashboardComponent addModule(DashboardModule dashboardModule);

    SessionComponent addModule(SessionModule sessionModule);

    CalendarComponent addModule(CalendarModule calendarModule);

    ScheduleComponent addModule(ScheduleModule scheduleModule);

    LateSendComponent addModule(LateSendModule lateSendModule);

}
