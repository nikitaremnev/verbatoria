package com.verbatoria.data.repositories.token;

import com.verbatoria.business.token.models.TokenModel;

/**
 *
 * Интерфейс-репозитория для токена
 *
 * @author nikitaremnev
 */
public interface ITokenRepository {

    TokenModel getToken();

    TokenModel updateToken(TokenModel tokenModel);

    String getStatus();

    void setStatus(String status);
}
