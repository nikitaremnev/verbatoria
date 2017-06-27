package com.verbatoria.business.login;

import com.verbatoria.data.network.response.LoginResponseModel;
import rx.Observable;

/**
 * Интерфейс интерактора для логина
 *
 * @author nikitaremnev
 */
public interface ILoginInteractor {

    Observable<LoginResponseModel> login(String phone, String password);

}
