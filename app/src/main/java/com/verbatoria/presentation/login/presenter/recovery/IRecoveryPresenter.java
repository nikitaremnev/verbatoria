package com.verbatoria.presentation.login.presenter.recovery;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.verbatoria.presentation.login.view.login.ILoginView;
import com.verbatoria.presentation.login.view.recovery.IRecoveryView;

/**
 * Презентер для восстановления пароля
 *
 * @author nikitaremnev
 */
public interface IRecoveryPresenter {

    void bindView(@NonNull IRecoveryView recoveryView);
    void unbindView();

    void rememberPassword();

    boolean confirmPassword(String password, String confirmedPassword);

    PasswordRequirements checkPasswordRequirements(String password);

    enum PasswordRequirements {
        TOO_SHORT, TOO_EASY, EMPTY, OK
    }

}
