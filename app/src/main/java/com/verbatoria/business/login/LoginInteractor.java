package com.verbatoria.business.login;

import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.business.token.processor.TokenProcessor;
import com.verbatoria.data.network.request.LoginRequestModel;
import com.verbatoria.data.network.response.LoginResponseModel;
import com.verbatoria.data.network.response.VerbatologEventResponseModel;
import com.verbatoria.data.repositories.login.ILoginRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;

import java.util.List;

import rx.Observable;
import rx.functions.Action0;

/**
 * Реализация интерактора для логина
 *
 * @author nikitaremnev
 */
public class LoginInteractor implements ILoginInteractor {

    private ILoginRepository mLoginRepository;
    private ITokenRepository mTokenRepository;

    public LoginInteractor(ILoginRepository loginRepository, ITokenRepository tokenRepository) {
        mLoginRepository = loginRepository;
        mTokenRepository = tokenRepository;
    }

    @Override
    public Observable<TokenModel> login(String phone, String password) {
        return mLoginRepository.getLogin(getLoginRequestModel(phone, password)).map(item -> {
            TokenProcessor tokenProcessor = new TokenProcessor();
            TokenModel tokenModel = tokenProcessor.obtainToken(item);
            mTokenRepository.updateToken(tokenModel);
            return tokenModel;
        });
    }

    private LoginRequestModel getLoginRequestModel(String phone, String password) {
        return new LoginRequestModel()
                .setPhone(processPhone(phone))
                .setPassword(password);
    }

    private String processPhone(String phone) {
        return phone.replaceAll("[^0-9.]", "");
    }

}
