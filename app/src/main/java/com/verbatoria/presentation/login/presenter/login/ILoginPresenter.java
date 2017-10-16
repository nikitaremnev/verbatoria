package com.verbatoria.presentation.login.presenter.login;

import android.support.annotation.NonNull;

import com.verbatoria.presentation.login.view.login.ILoginView;

/**
 * Презентер для логина
 *
 * @author nikitaremnev
 */
public interface ILoginPresenter {

    void bindView(@NonNull ILoginView loginView);
    void unbindView();

    void login();
    void startRecoveryPassword();

    void setUpLastLogin();

    String[] getCountryCodesArray();
}
