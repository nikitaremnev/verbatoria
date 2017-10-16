package com.verbatoria.business.login;

import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.response.MessageResponseModel;

import rx.Observable;

/**
 * Интерфейс интерактора для логина
 *
 * @author nikitaremnev
 */
public interface ILoginInteractor {

    Observable<TokenModel> login(String phone, String password);

    Observable<MessageResponseModel> recoveryPassword(String phone);

    Observable<MessageResponseModel> resetPassword(String phone, String code, String password);

    Void tryRecoveryCode(String phone, String code);

    Void setNewPassword(String phone, String password);

    String[] getCountryCodes();

    String getLastLogin();

}
