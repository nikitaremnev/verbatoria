package com.verbatoria.presentation.login.presenter.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.verbatoria.business.login.ILoginInteractor;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.login.view.login.ILoginView;
import com.verbatoria.utils.Logger;

/**
 * Реализация презентера для логина
 *
 * @author nikitaremnev
 */
public class LoginPresenter extends BasePresenter implements ILoginPresenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private ILoginInteractor mLoginInteractor;
    private ILoginView mLoginView;

    public LoginPresenter(ILoginInteractor loginInteractor) {
        this.mLoginInteractor = loginInteractor;
    }

    @Override
    public void bindView(@NonNull ILoginView loginView) {
        this.mLoginView = loginView;
    }

    @Override
    public void unbindView() {
        mLoginView = null;
    }

    @Override
    public void saveState(Bundle outState) {
        //TODO: state storing
    }

    @Override
    public void restoreState(Bundle savedInstanceState) {
        //TODO: state storing

    }

    @Override
    public void login() {
        mLoginView.showProgress();
        mLoginInteractor.login(mLoginView.getPhone(), mLoginView.getPassword())
                .subscribe(this::handleSuccessLogin, this::handleErrorLogin);
    }

    @Override
    public void startRecoveryPassword() {
        mLoginView.startRecoveryPassword();
    }

    @Override
    public String[] getCountryCodesArray() {
        return mLoginInteractor.getCountryCodes();
    }

    private void handleSuccessLogin(TokenModel tokenModel) {
        Logger.e(TAG, tokenModel.toString());
        mLoginView.hideProgress();
        mLoginView.loginSuccess();
    }

    private void handleErrorLogin(Throwable throwable) {
        Logger.exc(TAG, throwable);
        mLoginView.hideProgress();
        mLoginView.showError(throwable.getMessage());
    }

}
