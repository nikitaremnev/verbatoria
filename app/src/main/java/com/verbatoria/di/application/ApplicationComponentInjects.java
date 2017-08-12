package com.verbatoria.di.application;

import com.verbatoria.business.session.manager.AudioPlayerManager;
import com.verbatoria.business.session.activities.ActivitiesTimerTask;
import com.verbatoria.business.token.processor.TokenProcessor;
import com.verbatoria.presentation.dashboard.view.main.events.adapter.VerbatologEventsAdapter;
import com.verbatoria.presentation.login.presenter.LoginPresenter;
import com.verbatoria.presentation.session.view.submit.questions.QuestionViewHolder;
import com.verbatoria.presentation.session.view.submit.questions.QuestionsAdapter;

/**
 * Интерфейс для inject-методов для context
 *
 * @author nikitaremnev
 */
public interface ApplicationComponentInjects {

    void inject(LoginPresenter loginPresenter);

    void inject(TokenProcessor tokenProcessor);

    void inject(VerbatologEventsAdapter verbatologEventsAdapter);

    void inject(ActivitiesTimerTask activitiesTimerTask);

    void inject(AudioPlayerManager audioPlayerManager);

    void inject(QuestionsAdapter questionsAdapter);

    void inject(QuestionViewHolder questionViewHolder);
}
