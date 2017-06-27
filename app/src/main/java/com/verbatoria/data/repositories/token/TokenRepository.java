package com.verbatoria.data.repositories.token;

import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.utils.PreferencesStorage;

import java.util.concurrent.Callable;

import rx.Observable;

/**
 * Реализация репозитория для получения токена
 *
 * @author nikitaremnev
 */
public class TokenRepository implements ITokenRepository {

    @Override
    public Observable<TokenModel> getToken() {
        return Observable.fromCallable(new Callable<TokenModel>() {
            @Override
            public TokenModel call() throws Exception {
                TokenModel tokenModel = new TokenModel();
                tokenModel.setAccessToken(PreferencesStorage.getInstance().getAccessToken());
                tokenModel.setExpiresToken(PreferencesStorage.getInstance().getExpiresToken());
                return tokenModel;
            }
        });
    }

}
