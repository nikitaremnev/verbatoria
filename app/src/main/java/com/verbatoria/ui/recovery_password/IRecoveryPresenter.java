package com.verbatoria.ui.recovery_password;

import android.content.Intent;
import android.support.annotation.NonNull;

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
