package com.verbatoria.di.application;

import com.verbatoria.business.session.manager.AudioPlayerManager;
import com.verbatoria.business.session.activities.ActivitiesTimerTask;
import com.verbatoria.business.token.processor.TokenProcessor;
import com.verbatoria.data.repositories.late_send.LateSendRepository;
import com.verbatoria.presentation.calendar.view.adapter.EventsAdapter;
import com.verbatoria.presentation.calendar.view.add.children.search.ClientsAdapter;
import com.verbatoria.presentation.calendar.view.add.clients.search.ChildrenAdapter;
import com.verbatoria.presentation.login.presenter.login.LoginPresenter;
import com.verbatoria.presentation.session.view.submit.questions.QuestionViewHolder;
import com.verbatoria.presentation.session.view.submit.questions.QuestionsAdapter;

/**
 * Интерфейс для inject-методов для context
 *
 * @author nikitaremnev
 */
public interface ApplicationComponentInjects {

    void inject(LoginPresenter loginPresenter);

    void inject(LateSendRepository lateSendRepository);

    void inject(TokenProcessor tokenProcessor);

    void inject(EventsAdapter eventsAdapter);

    void inject(ClientsAdapter clientsAdapter);

    void inject(ChildrenAdapter childrenAdapter);

    void inject(ActivitiesTimerTask activitiesTimerTask);

    void inject(AudioPlayerManager audioPlayerManager);

    void inject(QuestionsAdapter questionsAdapter);

    void inject(QuestionViewHolder questionViewHolder);
}
