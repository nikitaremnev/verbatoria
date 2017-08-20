package com.verbatoria.presentation.session.presenter.reconnect;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.presentation.session.view.reconnect.IReconnectionView;

/**
 * Презентер для экрана соединения
 *
 * @author nikitaremnev
 */
public interface IReconnectionPresenter {

    void bindView(@NonNull IReconnectionView connectionView);
    void unbindView();

    void continueSession();
    void connect();

}
