package com.verbatoria.data.repositories.token;

import com.verbatoria.business.login.LoginInteractorException;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.business.token.processor.TokenProcessor;
import com.verbatoria.utils.PreferencesStorage;
import java.text.ParseException;

/**
 * Реализация репозитория для получения токена
 *
 * @author nikitaremnev
 */
public class TokenRepository implements ITokenRepository {

    @Override
    public TokenModel getToken() {
        TokenProcessor tokenProcessor = new TokenProcessor();
        return tokenProcessor.obtainToken(PreferencesStorage.getInstance().getAccessToken(),
                PreferencesStorage.getInstance().getExpiresToken());
    }

    @Override
    public TokenModel updateToken(TokenModel tokenModel) {
        PreferencesStorage.getInstance().setAccessToken(tokenModel.getAccessToken());
        try {
            PreferencesStorage.getInstance().setExpiresToken(tokenModel.getExpireDateString());
        } catch (ParseException e) {
            throw new LoginInteractorException(e.getMessage());
        }
        return tokenModel;
    }

}
