package com.verbatoria.data.repositories.login;

import com.verbatoria.data.network.response.LoginResponseModel;
import rx.Observable;

/**
 *
 * Реализация репозитория для логина
 *
 * @author nikitaremnev
 */


public class LoginRepository implements ILoginRepository {

    @Override
    public Observable<LoginResponseModel> getLoginInfo() {
        return null;
    }
}
