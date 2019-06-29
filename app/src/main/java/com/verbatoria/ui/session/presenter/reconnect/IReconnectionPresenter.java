package com.verbatoria.ui.session.presenter.reconnect;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.ui.session.view.reconnect.IReconnectionView;

/**
 * Презентер для экрана соединения
 *
 * @author nikitaremnev
 */
public interface IReconnectionPresenter {

    void bindView(@NonNull IReconnectionView connectionView);
    void unbindView();

    void obtainEvent(Intent intent);
    EventModel getEvent();

    void continueSession();
    void connect();

}
