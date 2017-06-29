package com.verbatoria.presentation.login.presenter;

import android.support.annotation.NonNull;
import com.verbatoria.business.login.ILoginInteractor;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.presentation.login.view.ILoginView;
import com.verbatoria.utils.Logger;
import com.verbatoria.utils.RxSchedulers;

/**
 * Реализация презентера для логина
 *
 * @author nikitaremnev
 */
public class LoginPresenter implements ILoginPresenter {

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
    public void login() {
        mLoginView.showProgress();
        mLoginInteractor.login(mLoginView.getPhone(), mLoginView.getPassword())
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler())
                .subscribe(this::handleSuccessLogin, this::handleErrorLogin);
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
