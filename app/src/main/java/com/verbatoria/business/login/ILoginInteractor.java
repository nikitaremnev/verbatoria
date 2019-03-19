package com.verbatoria.business.login;

import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.response.MessageResponseModel;
import com.verbatoria.data.network.response.SMSConfirmationResponseModel;

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

    Observable<SMSConfirmationResponseModel> sendSMSConfirmation(String phone, String text);

    Void tryRecoveryCode(String phone, String code);

    Void setNewPassword(String phone, String password);

    String[] getCountryCodes();

    String getLastLogin();

    Long getLastSmsConfirmationTimeInMillis();

    void updateLastSmsConfirmationTime(long time);

    void saveCountrySelection(String country);

    String getCountry();

    void saveSMSConfirmationCode(Long code);

    Long getSMSConfirmationCode();

}
