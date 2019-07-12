package com.verbatoria.business.login;

import com.verbatoria.data.repositories.login.ILoginRepository;
import com.verbatoria.utils.PreferencesStorage;

/**
 * Реализация интерактора для логина
 *
 * @author nikitaremnev
 */

public class AuthorizationInteractorImpl implements AuthorizationInteractor {

    private ILoginRepository mLoginRepository;

    public AuthorizationInteractorImpl(ILoginRepository loginRepository) {
        mLoginRepository = loginRepository;
    }

    @Override
    public String getLastLogin() {
        return mLoginRepository.lastLogin();
    }

    @Override
    public Long getLastSmsConfirmationTimeInMillis() {
        return mLoginRepository.lastSmsConfirmationTime();
    }

    @Override
    public void updateLastSmsConfirmationTime(long time) {
        mLoginRepository.updateLastSmsConfirmationTime(time);
    }

    @Override
    public String getCountry() {
        return PreferencesStorage.getInstance().getCountry();
    }

    @Override
    public void saveSMSConfirmationCode(Long code) {
        PreferencesStorage.getInstance().setLastSMSCode(code);
    }

    @Override
    public Long getSMSConfirmationCode() {
        return PreferencesStorage.getInstance().getLastSMSCode();
    }

}
