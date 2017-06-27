package com.verbatoria.business.token.interactor;

import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.response.LoginResponseModel;

import rx.Observable;
import rx.Single;

/**
 * Интерфейс интерактора для токена
 *
 * @author nikitaremnev
 */
public interface ITokenInteractor {

    Observable<TokenModel> getToken();

    Single<TokenModel> updateToken(LoginResponseModel loginResponseModel);

}
