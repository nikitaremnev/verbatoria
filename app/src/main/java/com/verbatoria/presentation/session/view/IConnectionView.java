package com.verbatoria.presentation.session.view;

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
