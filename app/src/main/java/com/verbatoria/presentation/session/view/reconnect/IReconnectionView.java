package com.verbatoria.presentation.session.view.reconnect;

/**
 *
 * View для экрана пересоединения
 *
 * @author nikitaremnev
 */
public interface IReconnectionView {

    void showProgress();
    void hideProgress();

    void startLoading();
    void showConnectingState();
    void showConnectedState();
    void showDisconnectedState();
    void showErrorConnectionState();
    void showBluetoothDisabled();

    void continueWriting();
    void showError(String error);

}
