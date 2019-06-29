package com.verbatoria.business.token.interactor;

import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.business.token.processor.TokenProcessor;
import com.verbatoria.data.network.response.LoginResponseModel;
import com.verbatoria.data.repositories.token.ITokenRepository;
import io.reactivex.Observable;
import rx.Single;

/**
 * Реализация интерактора для токена
 *
 * @author nikitaremnev
 */
public class TokenInteractor implements ITokenInteractor {

    private static final String TAG = TokenInteractor.class.getSimpleName();

    private ITokenRepository mTokenRepository;

    public TokenInteractor(ITokenRepository tokenRepository) {
        this.mTokenRepository = tokenRepository;
    }

    @Override
    public Observable<TokenModel> getToken() {
        return Observable.fromCallable(() -> mTokenRepository.getToken());
    }

    @Override
    public Single<TokenModel> updateToken(LoginResponseModel loginResponseModel) {
        return Single.fromCallable(() -> {
            TokenProcessor tokenProcessor = new TokenProcessor();
            return mTokenRepository.updateToken(tokenProcessor.obtainToken(loginResponseModel));
        });
    }

}
