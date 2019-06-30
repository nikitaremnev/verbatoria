package com.verbatoria.ui.login.presenter.recovery;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.ui.login.view.recovery.IRecoveryView;

/**
 * Презентер для восстановления пароля
 *
 * @author nikitaremnev
 */
public interface IRecoveryPresenter {

    void bindView(@NonNull IRecoveryView recoveryView);
    void unbindView();

    void obtainPhone(Intent intent);

    void rememberPassword();

    void recoveryPassword();
    void sendNewPassword();

    boolean confirmPassword();

    String getCountry();

    PasswordRequirements checkPasswordRequirements();

    enum PasswordRequirements {
        TOO_SHORT, TOO_EASY, EMPTY, OK
    }

}