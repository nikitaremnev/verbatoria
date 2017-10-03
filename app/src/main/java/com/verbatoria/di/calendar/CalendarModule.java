package com.verbatoria.di.calendar;

import com.verbatoria.business.children.ChildrenInteractor;
import com.verbatoria.business.children.IChildrenInteractor;
import com.verbatoria.business.clients.ClientsInteractor;
import com.verbatoria.business.clients.IClientsInteractor;
import com.verbatoria.data.repositories.children.ChildrenRepository;
import com.verbatoria.data.repositories.children.IChildrenRepository;
import com.verbatoria.data.repositories.clients.ClientsRepository;
import com.verbatoria.data.repositories.clients.IClientsRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.presentation.calendar.presenter.add.children.ChildrenPresenter;
import com.verbatoria.presentation.calendar.presenter.add.children.IChildrenPresenter;
import com.verbatoria.presentation.calendar.presenter.add.clients.ClientsPresenter;
import com.verbatoria.presentation.calendar.presenter.add.clients.IClientsPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Модуль даггера для календаря
 *
 * @author nikitaremnev
 */
@Module
public class CalendarModule {

    @Provides
    @CalendarScope
    IChildrenRepository provideChildrenRepository() {
        return new ChildrenRepository();
    }

    @Provides
    @CalendarScope
    IClientsRepository provideClientsRepository() {
        return new ClientsRepository();
    }

    @Provides
    @CalendarScope
    IChildrenInteractor provideChildrenInteractor(IChildrenRepository childrenRepository, ITokenRepository tokenRepository) {
        return new ChildrenInteractor(childrenRepository, tokenRepository);
    }

    @Provides
    @CalendarScope
    IClientsInteractor provideClientsInteractor(IClientsRepository clientsRepository, ITokenRepository tokenRepository) {
        return new ClientsInteractor(clientsRepository, tokenRepository);
    }

    @Provides
    @CalendarScope
    IChildrenPresenter provideChildrenPresenter(IChildrenInteractor childrenInteractor) {
        return new ChildrenPresenter(childrenInteractor);
    }

    @Provides
    @CalendarScope
    IClientsPresenter provideClientsPresenter(IClientsInteractor clientsInteractor) {
        return new ClientsPresenter(clientsInteractor);
    }

}
