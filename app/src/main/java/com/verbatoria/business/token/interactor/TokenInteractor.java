package com.verbatoria.business.token.interactor;

import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.response.LoginResponseModel;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.presentation.dashboard.presenter.DashboardPresenter;
import com.verbatoria.utils.Logger;

import rx.Observable;
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
        return mTokenRepository.getToken();
    }

    @Override
    public Single<TokenModel> updateToken(LoginResponseModel loginResponseModel) {
        TokenModel tokenModel = new TokenModel();
        tokenModel.setAccessToken(loginResponseModel.getAccessToken());
        tokenModel.setExpiresToken(loginResponseModel.getExpiresToken());
        Logger.e(TAG, tokenModel.toString());
        return mTokenRepository.updateToken(tokenModel);
    }

}
