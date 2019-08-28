package com.verbatoria.ui.session.presenter.reconnect;

import android.support.annotation.NonNull;
import com.verbatoria.ui.session.view.reconnect.IReconnectionView;

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
