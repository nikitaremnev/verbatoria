package com.verbatoria.presentation.login.view.recovery;

/**
 *
 * View для экрана логина
 *
 * @author nikitaremnev
 */
public interface IRecoveryView {

    String getPhone();

    String getCode();

    void setPhone(String phone);

    String getNewPassword();

    String getNewConfirmPassword();

    void showProgress();

    void hideProgress();

    void showPhoneInput();

    void showCodeInput();

    void showNewPasswordInput();

    void showError(String message);

    void rememberPassword();

}
