package com.verbatoria.business.login;

/**
 * Интерфейс интерактора для логина
 *
 * @author nikitaremnev
 */
public interface AuthorizationInteractor {

    String getLastLogin();

    Long getLastSmsConfirmationTimeInMillis();

    void updateLastSmsConfirmationTime(long time);

    String getCountry();

    void saveSMSConfirmationCode(Long code);

    Long getSMSConfirmationCode();

}
