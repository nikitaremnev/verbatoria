package com.verbatoria.presentation.session.view.connection;

/**
 *
 * View для экрана соединения
 *
 * @author nikitaremnev
 */
public interface IConnectionView {

    void showProgress();
    void hideProgress();

    void startLoading();
    void showConnectingState();
    void showConnectedState();
    void showDisconnectedState();
    void showErrorConnectionState();
    void showBluetoothDisabled();

    void startWriting();
    void showError(String error);

}
