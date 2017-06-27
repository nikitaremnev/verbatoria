package com.verbatoria.business.login;

import com.verbatoria.data.network.request.LoginRequestModel;
import com.verbatoria.data.network.response.LoginResponseModel;
import com.verbatoria.data.repositories.login.ILoginRepository;

import rx.Observable;

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

    @Override
    public Observable<LoginResponseModel> login(String phone, String password) {
        return mLoginRepository.getLogin(getLoginRequestModel(phone, password));
    }

    private LoginRequestModel getLoginRequestModel(String phone, String password) {
        return new LoginRequestModel()
                .setPhone(phone)
                .setPassword(password);
    }
}
