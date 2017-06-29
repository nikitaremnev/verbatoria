package com.verbatoria.data.repositories.token;

import com.verbatoria.business.token.models.TokenModel;

import rx.Observable;
import rx.Single;

/**
 *
 * Интерфейс-репозитория для токена
 *
 * @author nikitaremnev
 */
public interface ITokenRepository {

    TokenModel getToken();

    TokenModel updateToken(TokenModel tokenModel);

}
