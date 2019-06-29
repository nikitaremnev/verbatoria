package com.verbatoria.di.session;

import com.verbatoria.data.repositories.session.SessionRepository;
import com.verbatoria.ui.session.view.connection.ConnectionActivity;
import com.verbatoria.ui.session.view.reconnect.ReconnectionActivity;
import com.verbatoria.ui.session.view.submit.SubmitActivity;
import com.verbatoria.ui.session.view.submit.school.SchoolSubmitActivity;
import com.verbatoria.ui.session.view.writing.WritingActivity;

import dagger.Subcomponent;

/**
 * Компонент Даггера для логина
 *
 * @author nikitaremnev
 */
@Subcomponent(modules = {SessionModule.class})
@SessionScope
public interface SessionComponent {
    
    void inject(ConnectionActivity connectionActivity);

    void inject(WritingActivity writingActivity);

    void inject(SessionRepository sessionRepository);

    void inject(SubmitActivity submitActivity);

    void inject(SchoolSubmitActivity schoolSubmitActivity);

    void inject(ReconnectionActivity reconnectionActivity);
}
