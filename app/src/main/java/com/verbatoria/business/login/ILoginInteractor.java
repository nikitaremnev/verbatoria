package com.verbatoria.business.login;

import com.verbatoria.business.token.models.TokenModel;
import rx.Observable;

/**
 * Интерфейс интерактора для логина
 *
 * @author nikitaremnev
 */
public interface ILoginInteractor {

    Observable<TokenModel> login(String phone, String password);

    Void recoverPassword(String phone);

    Void tryRecoveryCode(String phone, String code);

    Void setNewPassword(String phone, String password);

    String[] getCountryCodes();

}
