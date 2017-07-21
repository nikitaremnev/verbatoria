package com.verbatoria.di.session;

import com.verbatoria.presentation.dashboard.view.calendar.detail.CalendarEventDetailActivity;
import com.verbatoria.presentation.session.view.connection.ConnectionActivity;
import com.verbatoria.presentation.session.view.writing.WritingActivity;

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

    void inject(WritingActivity writingActivity);

}
