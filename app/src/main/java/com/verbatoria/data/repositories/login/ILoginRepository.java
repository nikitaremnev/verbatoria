package com.verbatoria.data.repositories.login;

import com.verbatoria.data.network.request.LoginRequestModel;
import com.verbatoria.data.network.request.RecoveryPasswordRequestModel;
import com.verbatoria.data.network.request.ResetPasswordRequestModel;
import com.verbatoria.data.network.response.LoginResponseModel;
import com.verbatoria.data.network.response.MessageResponseModel;

import rx.Observable;

/**
 *
 * Интерфейс-репозитория для логина
 *
 * @author nikitaremnev
 */
public interface ILoginRepository {

    Observable<LoginResponseModel> getLogin(LoginRequestModel loginRequestModel);

    Observable<MessageResponseModel> recoveryPassword(RecoveryPasswordRequestModel recoveryPasswordRequestModel);

    Observable<MessageResponseModel> resetPassword(ResetPasswordRequestModel resetPasswordRequestModel);

    String lastLogin();

    void updateLastLogin(String login);

}
