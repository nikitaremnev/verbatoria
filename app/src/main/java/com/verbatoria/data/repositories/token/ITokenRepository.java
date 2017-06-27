package com.verbatoria.data.repositories.token;

import com.verbatoria.business.token.models.TokenModel;

import rx.Observable;

/**
 *
 * Интерфейс-репозитория для токена
 *
 * @author nikitaremnev
 */
public interface ITokenRepository {

    Observable<TokenModel> getToken();

}
