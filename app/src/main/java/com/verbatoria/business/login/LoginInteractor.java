package com.verbatoria.business.login;

import com.verbatoria.data.repositories.login.ILoginRepository;

/**
 * Реализация интерактора для логина
 *
 * @author nikitaremnev
 */

public class LoginInteractor implements ILoginInteractor {

    private ILoginRepository mLoginRepository;

    public LoginInteractor(ILoginRepository loginRepository) {
        this.mLoginRepository = loginRepository;
    }

}
