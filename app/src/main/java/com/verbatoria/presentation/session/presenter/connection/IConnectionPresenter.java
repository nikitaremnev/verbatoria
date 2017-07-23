package com.verbatoria.presentation.session.presenter.connection;

import android.content.Intent;
import android.support.annotation.NonNull;
import com.verbatoria.presentation.session.view.connection.IConnectionView;

/**
 * Презентер для экрана соединения
 *
 * @author nikitaremnev
 */
public interface IConnectionPresenter {

    void bindView(@NonNull IConnectionView connectionView);
    void unbindView();

    void obtainEvent(Intent intent);
    void startSession();
    void connect();

}
