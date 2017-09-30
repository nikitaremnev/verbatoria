package com.verbatoria.di.calendar;

import com.verbatoria.business.children.ChildrenInteractor;
import com.verbatoria.business.children.IChildrenInteractor;
import com.verbatoria.business.clients.ClientsInteractor;
import com.verbatoria.business.clients.IClientsInteractor;
import com.verbatoria.data.repositories.children.ChildrenRepository;
import com.verbatoria.data.repositories.children.IChildrenRepository;
import com.verbatoria.data.repositories.clients.ClientsRepository;
import com.verbatoria.data.repositories.clients.IClientsRepository;
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
    IChildrenInteractor provideChildrenInteractor(IChildrenRepository childrenRepository) {
        return new ChildrenInteractor(childrenRepository);
    }

    @Provides
    @CalendarScope
    IClientsInteractor provideClientsInteractor(IClientsRepository clientsRepository) {
        return new ClientsInteractor(clientsRepository);
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
