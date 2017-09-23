package com.verbatoria.business.login;

import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.business.token.processor.TokenProcessor;
import com.verbatoria.data.network.request.LoginRequestModel;
import com.verbatoria.data.repositories.login.ILoginRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.utils.RxSchedulers;

import rx.Observable;

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
                })
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Void recoverPassword(String phone) {
        return null;
    }

    @Override
    public Void tryRecoveryCode(String phone, String code) {
        return null;
    }

    @Override
    public Void setNewPassword(String phone, String password) {
        return null;
    }

    @Override
    public String[] getCountryCodes() {
        return new String[] {"+7"};
    }


    private LoginRequestModel getLoginRequestModel(String phone, String password) {
        return new LoginRequestModel()
                .setPhone(processPhone(phone))
                .setPassword(password);
    }

    private String processPhone(String phone) {
        return phone.replaceAll("[-,. )(]", "");
    }

}
