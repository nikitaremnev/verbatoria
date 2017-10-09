package com.verbatoria.presentation.calendar.view.add.clients;

import com.verbatoria.data.network.common.ClientModel;

import java.util.List;

/**
 * Интерфейс вьюхи для добавления события в календарь
 *
 * @author nikitaremnev
 */
public interface ISearchClientsView {

    //отображение прогресса
    void showProgress();
    void hideProgress();

    String getQuery();

    void showClientsFound(List<ClientModel> clients);

    void showError(String message);

}
