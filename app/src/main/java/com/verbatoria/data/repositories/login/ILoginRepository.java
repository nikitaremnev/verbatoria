package com.verbatoria.data.repositories.login;

import com.verbatoria.data.network.response.LoginResponseModel;

import rx.Observable;

/**
 *
 * Интерфейс-репозитория для логина
 *
 * @author nikitaremnev
 */

public interface ILoginRepository {

    Observable<LoginResponseModel> getLoginInfo();

}
