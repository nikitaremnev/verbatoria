package com.verbatoria.data.repositories.login;

import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.request.LoginRequestModel;
import com.verbatoria.data.network.request.RecoveryPasswordRequestModel;
import com.verbatoria.data.network.request.ResetPasswordRequestModel;
import com.verbatoria.data.network.response.LoginResponseModel;
import com.verbatoria.data.network.response.MessageResponseModel;
import com.verbatoria.utils.PreferencesStorage;

import rx.Observable;

/**
 *
 * Реализация репозитория для логина
 *
 * @author nikitaremnev
 */
public class LoginRepository implements ILoginRepository {

    @Override
    public Observable<LoginResponseModel> getLogin(LoginRequestModel loginRequestModel) {
        return APIFactory.getAPIService().loginRequest(loginRequestModel);
    }

    @Override
    public Observable<MessageResponseModel> recoveryPassword(RecoveryPasswordRequestModel recoveryPasswordRequestModel) {
        return APIFactory.getAPIService().recoveryPassword(recoveryPasswordRequestModel);
    }

    @Override
    public Observable<MessageResponseModel> resetPassword(ResetPasswordRequestModel resetPasswordRequestModel) {
        return APIFactory.getAPIService().resetPassword(resetPasswordRequestModel);
    }

    @Override
    public String lastLogin() {
        return PreferencesStorage.getInstance().getLastLogin();
    }

    @Override
    public void updateLastLogin(String login) {
        PreferencesStorage.getInstance().setLastLogin(login);
    }
}
