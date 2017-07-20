package com.verbatoria.di.session;

import com.verbatoria.di.login.LoginModule;
import com.verbatoria.di.login.LoginScope;
import com.verbatoria.di.token.TokenModule;
import com.verbatoria.presentation.dashboard.view.calendar.detail.CalendarEventDetailActivity;
import com.verbatoria.presentation.login.view.LoginActivity;
import com.verbatoria.presentation.session.view.ConnectionActivity;

import java.sql.Connection;

import dagger.Subcomponent;

/**
 * Компонент Даггера для логина
 *
 * @author nikitaremnev
 */
@Subcomponent(modules = {SessionModule.class})
@SessionScope
public interface SessionComponent {

    void inject(CalendarEventDetailActivity calendarEventDetailActivity);

    void inject(ConnectionActivity connectionActivity);

}
