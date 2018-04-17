package com.verbatoria.business.login;

import android.util.Log;

import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.business.token.processor.TokenProcessor;
import com.verbatoria.data.network.request.LoginRequestModel;
import com.verbatoria.data.network.request.RecoveryPasswordRequestModel;
import com.verbatoria.data.network.request.ResetPasswordRequestModel;
import com.verbatoria.data.network.response.MessageResponseModel;
import com.verbatoria.data.repositories.login.ILoginRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.utils.PreferencesStorage;
import com.verbatoria.utils.RxSchedulers;

import rx.Observable;

/**
 * Реализация интерактора для логина
 *
 * @author nikitaremnev
 */
public class LoginInteractor implements ILoginInteractor {

    private static final String TAG = LoginInteractor.class.getSimpleName();

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
                    Log.e("test", "login(String phone, String password): " + tokenModel.getStatus());
                    mTokenRepository.setStatus(tokenModel.getStatus());
                    mLoginRepository.updateLastLogin(phone);
                    return tokenModel;
                })
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Observable<MessageResponseModel> recoveryPassword(String phone) {
        return mLoginRepository.recoveryPassword(getRecoveryPasswordRequestModel(phone))
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }


    @Override
    public Observable<MessageResponseModel> resetPassword(String phone, String code, String password) {
        return mLoginRepository.resetPassword(getResetPasswordRequestModel(phone, code, password))
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
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

    @Override
    public String getLastLogin() {
        return mLoginRepository.lastLogin();
    }

    @Override
    public void saveCountrySelection(String country) {
        PreferencesStorage.getInstance().setCountry(country);
    }

    @Override
    public String getCountry() {
        return PreferencesStorage.getInstance().getCountry();
    }

    private LoginRequestModel getLoginRequestModel(String phone, String password) {
        return new LoginRequestModel()
                .setPhone(processPhone(phone))
                .setPassword(password);
    }

    private RecoveryPasswordRequestModel getRecoveryPasswordRequestModel(String phone) {
        return new RecoveryPasswordRequestModel()
                .setPhone(processPhone(phone));
    }

    private ResetPasswordRequestModel getResetPasswordRequestModel(String phone, String code, String password) {
        return new ResetPasswordRequestModel()
                .setPhone(processPhone(phone))
                .setPassword(password)
                .setRecoveryHash(code);
    }

    private String processPhone(String phone) {
        return phone.replaceAll("[-,. )(]", "");
    }

}
