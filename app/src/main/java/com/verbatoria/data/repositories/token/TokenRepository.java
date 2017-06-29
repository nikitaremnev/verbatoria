package com.verbatoria.data.repositories.token;

import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.business.token.processor.TokenProcessor;
import com.verbatoria.utils.PreferencesStorage;
import rx.Single;

/**
 * Реализация репозитория для получения токена
 *
 * @author nikitaremnev
 */
public class TokenRepository implements ITokenRepository {

    @Override
    public TokenModel getToken() {
        TokenModel tokenModel = new TokenModel();
        tokenModel.setAccessToken(PreferencesStorage.getInstance().getAccessToken());
        tokenModel.setExpiresToken(PreferencesStorage.getInstance().getExpiresToken());
        return tokenModel;
    }

    @Override
    public TokenModel updateToken(TokenModel tokenModel) {
        PreferencesStorage.getInstance().setAccessToken(tokenModel.getAccessToken());
        PreferencesStorage.getInstance().setExpiresToken(tokenModel.getExpiresToken());
        return tokenModel;
    }

}
