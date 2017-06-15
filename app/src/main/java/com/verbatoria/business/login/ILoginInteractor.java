package com.verbatoria.business.login;

import com.verbatoria.data.network.request.LoginRequestModel;
import com.verbatoria.data.network.response.LoginResponseModel;

import rx.Observable;
import rx.Single;

/**
 * Интерфейс интерактора для логина
 *
 * @author nikitaremnev
 */

public interface ILoginInteractor {

    Observable<LoginResponseModel> login(String phone, String password);

}
