package com.verbatoria.business.login;

import com.verbatoria.data.network.response.MessageResponseModel;
import io.reactivex.Observable;

/**
 * Интерфейс интерактора для логина
 *
 * @author nikitaremnev
 */
public interface AuthorizationInteractor {

    Observable<MessageResponseModel> recoveryPassword(String phone);

    Observable<MessageResponseModel> resetPassword(String phone, String code, String password);

    String getLastLogin();

    Long getLastSmsConfirmationTimeInMillis();

    void updateLastSmsConfirmationTime(long time);

    String getCountry();

    void saveSMSConfirmationCode(Long code);

    Long getSMSConfirmationCode();

}
