package com.verbatoria.presentation.login.view.recovery;

/**
 *
 * View для экрана логина
 *
 * @author nikitaremnev
 */
public interface IRecoveryView {

    String getPhone();
    String getNewPassword();
    String confirmPasswords(String password, String confirmPassword);

    void showProgress();
    void hideProgress();

    void sendCodeToPhone();
    void sendNewPassword();

    void showPhoneInput();
    void showCodeInput();
    void showNewPasswordInput();

    void showError(String message);

    void rememberPassword();
}
