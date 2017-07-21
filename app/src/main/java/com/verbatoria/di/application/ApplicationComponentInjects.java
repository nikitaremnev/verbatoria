package com.verbatoria.di.application;

import com.verbatoria.business.session.manager.AudioPlayerManager;
import com.verbatoria.business.session.timer.ActivitiesTimerTask;
import com.verbatoria.business.token.processor.TokenProcessor;
import com.verbatoria.presentation.dashboard.view.main.events.adapter.VerbatologEventsAdapter;

/**
 * Интерфейс для inject-методов для context
 *
 * @author nikitaremnev
 */
public interface ApplicationComponentInjects {

    void inject(TokenProcessor tokenProcessor);

    void inject(VerbatologEventsAdapter verbatologEventsAdapter);

    void inject(ActivitiesTimerTask activitiesTimerTask);

    void inject(AudioPlayerManager audioPlayerManager);
}
