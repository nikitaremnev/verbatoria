package com.verbatoria.data.repositories.token;

import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.utils.PreferencesStorage;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Single;

/**
 * Реализация репозитория для получения токена
 *
 * @author nikitaremnev
 */
public class TokenRepository implements ITokenRepository {

    @Override
    public Observable<TokenModel> getToken() {
        return Observable.fromCallable(() -> {
            TokenModel tokenModel = new TokenModel();
            tokenModel.setAccessToken(PreferencesStorage.getInstance().getAccessToken());
            tokenModel.setExpiresToken(PreferencesStorage.getInstance().getExpiresToken());
            return tokenModel;
        });
    }

    @Override
    public Single<TokenModel> updateToken(TokenModel tokenModel) {
        return Single.fromCallable(() -> {
            PreferencesStorage.getInstance().setAccessToken(tokenModel.getAccessToken());
            PreferencesStorage.getInstance().setExpiresToken(tokenModel.getExpiresToken());
            return tokenModel;
        });
    }

}
