package com.verbatoria.di.application;

import com.verbatoria.business.session.manager.AudioPlayerManager;
import com.verbatoria.business.session.activities.ActivitiesTimerTask;
import com.verbatoria.business.token.processor.TokenProcessor;
import com.verbatoria.presentation.dashboard.view.main.events.adapter.VerbatologEventsAdapter;
import com.verbatoria.presentation.session.view.submit.adapter.QuestionViewHolder;
import com.verbatoria.presentation.session.view.submit.adapter.QuestionsAdapter;

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

    void inject(QuestionsAdapter questionsAdapter);

    void inject(QuestionViewHolder questionViewHolder);
}
