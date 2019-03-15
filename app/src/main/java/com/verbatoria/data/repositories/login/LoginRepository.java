package com.verbatoria.data.repositories.login;

import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.request.LoginRequestModel;
import com.verbatoria.data.network.request.RecoveryPasswordRequestModel;
import com.verbatoria.data.network.request.ResetPasswordRequestModel;
import com.verbatoria.data.network.response.LoginResponseModel;
import com.verbatoria.data.network.response.MessageResponseModel;
import com.verbatoria.data.network.response.SMSConfirmationResponseModel;
import com.verbatoria.utils.PreferencesStorage;

import rx.Observable;

/**
 *
 * Реализация репозитория для логина
 *
 * @author nikitaremnev
 */
public class LoginRepository implements ILoginRepository {

    private static final String VERBATORIA_SMS_TOKEN = "eb7c35c2-32c5-414c-bf6c-89470be41ca1";

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
    public Observable<SMSConfirmationResponseModel> sendSMSConfirmation(String phone, String text) {
        return APIFactory.getPanelAPIService().sendSMSConfirmation(phone, text, VERBATORIA_SMS_TOKEN);
    }

    @Override
    public String lastLogin() {
        return PreferencesStorage.getInstance().getLastLogin();
    }

    @Override
    public void updateLastLogin(String login) {
        PreferencesStorage.getInstance().setLastLogin(login);
    }

    @Override
    public void updateLocationId(String locationId) {
        PreferencesStorage.getInstance().setLocationId(locationId);
    }

}
