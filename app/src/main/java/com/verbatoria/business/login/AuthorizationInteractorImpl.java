package com.verbatoria.business.login;

import com.verbatoria.data.network.request.RecoveryPasswordRequestModel;
import com.verbatoria.data.network.request.ResetPasswordRequestModel;
import com.verbatoria.data.network.response.MessageResponseModel;
import com.verbatoria.data.repositories.login.ILoginRepository;
import com.verbatoria.utils.PreferencesStorage;
import com.verbatoria.utils.RxSchedulers;
import io.reactivex.Observable;

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
