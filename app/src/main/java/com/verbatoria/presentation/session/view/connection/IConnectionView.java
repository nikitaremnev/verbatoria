package com.verbatoria.presentation.session.view.connection;

/**
 *
 * View для экрана соединения
 *
 * @author nikitaremnev
 */
public interface IConnectionView {

    void showConnectingState();
    void showConnectedState();
    void showDisconnectedState();
    void showErrorConnectionState();
    void showBluetoothDisabled();
}
