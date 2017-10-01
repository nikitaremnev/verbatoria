package com.verbatoria.di.session;

import com.verbatoria.data.repositories.session.SessionRepository;
import com.verbatoria.presentation.calendar.view.detail.EventDetailActivity;
import com.verbatoria.presentation.session.view.connection.ConnectionActivity;
import com.verbatoria.presentation.session.view.reconnect.ReconnectionActivity;
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

    void inject(EventDetailActivity eventDetailActivity);

    void inject(ConnectionActivity connectionActivity);

    void inject(WritingActivity writingActivity);

    void inject(SessionRepository sessionRepository);

    void inject(SubmitActivity submitActivity);

    void inject(ReconnectionActivity reconnectionActivity);
}
