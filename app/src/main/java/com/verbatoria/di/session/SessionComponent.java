package com.verbatoria.di.session;

import com.verbatoria.data.repositories.session.SessionRepository;
import com.verbatoria.presentation.dashboard.view.calendar.detail.CalendarEventDetailActivity;
import com.verbatoria.presentation.login.view.LoginActivity;
import com.verbatoria.presentation.session.view.connection.ConnectionActivity;
import com.verbatoria.presentation.session.view.submit.SubmitActivity;
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

    void inject(SessionRepository sessionRepository);

    void inject(SubmitActivity submitActivity);
}
