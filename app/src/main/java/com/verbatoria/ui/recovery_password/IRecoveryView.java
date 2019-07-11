package com.verbatoria.ui.recovery_password;

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
