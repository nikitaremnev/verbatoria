package com.verbatoria.ui.session.presenter.connection;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.ui.session.view.connection.IConnectionView;

/**
 * Презентер для экрана соединения
 *
 * @author nikitaremnev
 */
public interface IConnectionPresenter {

    void bindView(@NonNull IConnectionView connectionView);
    void unbindView();

    void obtainEvent(Intent intent);
    EventModel getEvent();

    void startWriting();

    void connect();

}