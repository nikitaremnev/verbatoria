package com.verbatoria.presentation.calendar.presenter.add.clients;

import android.support.annotation.NonNull;

import com.verbatoria.presentation.calendar.view.add.clients.IClientsView;

/**
 * Презентер для добавления события в календарь
 *
 * @author nikitaremnev
 */
public interface IClientsPresenter {

    void bindView(@NonNull IClientsView clientView);
    void unbindView();

}
