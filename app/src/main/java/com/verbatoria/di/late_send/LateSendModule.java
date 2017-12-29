package com.verbatoria.di.late_send;

import com.verbatoria.business.late_send.ILateSendInteractor;
import com.verbatoria.business.late_send.LateSendInteractor;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.data.repositories.late_send.ILateSendRepository;
import com.verbatoria.data.repositories.late_send.LateSendRepository;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.di.session.SessionScope;
import com.verbatoria.presentation.late_send.presenter.ILateSendPresenter;
import com.verbatoria.presentation.late_send.presenter.LateSendPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Модуль даггера для поздней отпрвки
 *
 * @author nikitaremnev
 */
@Module
public class LateSendModule {

    @Provides
    @SessionScope
    ILateSendRepository provideLateSendRepository() {
        return new LateSendRepository();
    }

    @Provides
    @SessionScope
    ILateSendInteractor provideLateSendInteractor(ILateSendRepository lateSendRepository,
                                                  ISessionRepository sessionRepository,
                                                  ITokenRepository tokenRepository) {
        return new LateSendInteractor(lateSendRepository, sessionRepository, tokenRepository);
    }

    @Provides
    @SessionScope
    ILateSendPresenter provideLateSendPresenter(ILateSendInteractor lateSendInteractor,
                                                ISessionInteractor sessionInteractor) {
        return new LateSendPresenter(lateSendInteractor, sessionInteractor);
    }

}
